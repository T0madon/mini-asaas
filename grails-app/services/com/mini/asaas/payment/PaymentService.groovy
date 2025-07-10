package com.mini.asaas.payment

import com.mini.asaas.base.BasePaymentAdapter
import com.mini.asaas.customer.Customer
import com.mini.asaas.email.EmailService
import com.mini.asaas.enums.NotificationType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.notification.NotificationService
import com.mini.asaas.payer.Payer
import com.mini.asaas.Payment.Payment
import com.mini.asaas.utils.DateFormatUtils
import com.mini.asaas.utils.DomainErrorUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException


@Transactional
@GrailsCompileStatic
class PaymentService {

    EmailService emailService

    NotificationService notificationService

    public Payment save(Long customerId, PaymentSaveAdapter adapter) {
        Payment payment = new Payment()
        Customer customer = Customer.get(customerId)

        validate(adapter, payment)

        if (payment.hasErrors()) {
            throw new ValidationException(" Falha ao realizar operação no pagamento: ", payment.errors)
        }

        buildPayment(adapter, payment)

        payment.customer = customer
        payment.save(failOnError: true)
        emailService.notifyPaymentCreated(payment)

        notificationService.create(
                [payment.id] as Object[],
                [payment.value, payment.payer.name] as Object[],
                payment.customer,
                NotificationType.PAYMENT_CREATED,
                "created",
                payment.id,
        )
        return payment
    }

    public Payment update(Long customerId, PaymentUpdateAdapter adapter) {
        Payment payment = PaymentRepository.query([id: adapter.id, customerId: customerId]).get()
        if (!payment) throw new RuntimeException("Pagamento não encontrado")
        validateUpdate(adapter, payment)

        if (payment.hasErrors()) {
            throw new ValidationException(" Falha ao atualizar pagamento: ", payment.errors)
        }

        payment.payer = Payer.get(adapter.payerId)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.status = adapter.status
        payment.description = adapter.description
        payment.dueDate = adapter.dueDate

        payment.save(failOnError: true)
        return payment
    }

    public Payment findById(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([id: id, customerId: customerId, includeDeleted: true]).readOnly().get()
        if (!payment) throw new RuntimeException("Cobrança não encontrada")
        return payment
    }

    public void delete(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([customerId: customerId, id: id]).get()

        if (!payment) throw new RuntimeException("Cobrança não encontrada")
        if (!payment.status.canBeDeleted()) throw new BusinessException("Cobrança não pode ser deletada")

        payment.deleted = true
        payment.status = PaymentStatus.CANCELED
        payment.save(failOnError: true)
        emailService.notifyPaymentDeleted(payment)
        notificationService.create(
                [payment.id] as Object[],
                [payment.value, payment.payer.name] as Object[],
                payment.customer,
                NotificationType.PAYMENT_DELETED,
                "deleted",
                payment.id,
        )
    }

    public void restore(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([customerId: customerId, id: id, deletedOnly: true]).get()
        if (!payment) throw new RuntimeException("Cobrança não encontrada")

        payment.deleted = false
        payment.status = PaymentStatus.PENDING

        payment.save(failOnError: true)
    }

    public List<Payment> list(Long customerId, List<String> statusFilterList, Integer max, Integer offset) {
        Map queryParams = [customerId: customerId, includeDeleted: true, "status[in]": statusFilterList]
        return PaymentRepository.query(queryParams).readOnly().list([max: max, offset: offset])
    }

    public void receive(Long id) {
        Payment payment = PaymentRepository.get(id)
        if (!payment) throw new RuntimeException("Cobrança não encontrada")
        if (!payment.status.canBeReceived()) throw new BusinessException("Apenas cobranças com status 'Aguardando pagamento' podem ser recebidas")

        payment.status = PaymentStatus.RECEIVED
        payment.paymentDate = new Date()
        emailService.notifyPaymentReceive(payment)
        notificationService.create(
                [payment.id] as Object[],
                [payment.value, payment.payer.name] as Object[],
                payment.customer,
                NotificationType.PAYMENT_RECEIVED,
                "received",
                payment.id,
        )

        payment.save(failOnError: true)
    }

    public void setPaymentOverdue() {
        Map params = [
                "dueDate[lt]": DateFormatUtils.getDateWithoutTime(),
                status       : PaymentStatus.PENDING
        ]

        List<Long> paymentIdList = PaymentRepository.query(params).column("id").list()

        for (Long id : paymentIdList) {
            Payment.withNewTransaction { status ->
                try {
                    Payment payment = PaymentRepository.get(id)
                    payment.status = PaymentStatus.OVERDUE
                    payment.save(failOnError: true)
                    emailService.notifyPaymentOverdue(payment)
                    notificationService.create(
                            [payment.id] as Object[],
                            [payment.value, payment.payer.name, payment.dueDate] as Object[],
                            payment.customer,
                            NotificationType.PAYMENT_OVERDUE,
                            "overdue",
                            payment.id,
                    )
                } catch (Exception exception) {
                    log.error("Erro ao processar pagamento", exception)
                    throw new RuntimeException("Erro ao processar pagamento!" + exception)
                }
            }
        }
    }

    private void validateUpdate(PaymentUpdateAdapter adapter, Payment validatedPayment) {
        validate(adapter, validatedPayment)

        if (!adapter.id) DomainErrorUtils.addError(validatedPayment, "Campo Id vazio")
    }

    private void validate(BasePaymentAdapter adapter, Payment validatedPayment) {
        Payer payer = Payer.get(adapter.payerId)

        if (!adapter.payerId) DomainErrorUtils.addError(validatedPayment, "Campo payerId vazio")

        if (!payer || payer.deleted) DomainErrorUtils.addError(validatedPayment, "Pagador Inválido")

        if (!adapter.value) DomainErrorUtils.addError(validatedPayment, "Campo valor vazio")

        if (adapter.value <= 0) DomainErrorUtils.addError(validatedPayment, "Valor inválido")

        if (!adapter.billingType) DomainErrorUtils.addError(validatedPayment, "Campo tipo de pagamento vazio")

        if (adapter.dueDate && adapter.dueDate < new Date()) DomainErrorUtils.addError(validatedPayment, "A data de vencimento deve ser posterior à data atual")

        if (!(adapter.billingType in BillingType.values())) DomainErrorUtils.addError(validatedPayment, "Tipo de cobrança inválido")

    }

    private void buildPayment(PaymentSaveAdapter adapter, Payment payment) {
        payment.payer = Payer.get(adapter.payerId)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.status = adapter.status
        payment.description = adapter.description
        payment.dueDate = adapter.dueDate
    }

}
