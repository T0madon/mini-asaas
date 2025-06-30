package com.mini.asaas.payment

import com.mini.asaas.Payment.Payment
import com.mini.asaas.base.BasePaymentAdapter
import com.mini.asaas.customer.Customer
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.payer.Payer
import com.mini.asaas.user.User
import com.mini.asaas.utils.DomainErrorUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
@GrailsCompileStatic
class PaymentService {

    public Payment save(Long customerId, PaymentSaveAdapter adapter) {
        Payment payment = new Payment()
        Customer customer = Customer.get(customerId)

        validateSave(adapter, payment)

        if (payment.hasErrors()) throw new ValidationException("Falha ao salvar novo Pagamento", payment.errors as String)

        buildPayment(adapter, payment)

        payment.customer = customer
        payment.save(failOnError: true)
        return payment
    }

    public Payment update(Long customerId, PaymentUpdateAdapter adapter) {
        Payment payment = PaymentRepository.query([id: adapter.id, customerId: customerId]).get()
        if (!payment) throw new RuntimeException("Pagamento não encontrado")

        validateUpdate(adapter, payment)

        if (payment.hasErrors()) throw new ValidationException("Falha ao atualizar o Pagador", payment.errors as String)

        payment.payer = Payer.get(adapter.payerId)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.status = adapter.status
        payment.description = adapter.description
        payment.dueDate = adapter.dueDate

        payment.save(failOnError: true)
        return payment
    }

    public void delete(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([customerId: customerId, id: id]).get()

        if (!payment) throw new RuntimeException("Cobrança não encontrada")
        if (!payment.status.canBeDeleted()) throw new BusinessException("Cobrança não pode ser deletada")

        payment.deleted = true
        payment.save(failOnError: true)
    }

    public void restore(Long customerId, Long id) {
        Payment payment = PaymentRepository.query([deletedOnly: true, customerId: customerId, id: id]).get()
        if (!payment) throw new RuntimeException("Cobrança não encontrada")
        if (payment.dueDate < new Date()) throw new BusinessException("A data de vencimento não pode ser uma data passada")

        payment.deleted = false
        payment.status = PaymentStatus.PENDING

        payment.save(failOnError: true)
    }

    private void validateSave(PaymentSaveAdapter adapter, Payment validatedPayment) {
        validateCommonData(adapter, validatedPayment)

    }

    private void validateUpdate(PaymentUpdateAdapter adapter, Payment validatedPayment) {
        validateCommonData(adapter, validatedPayment)

        if (!adapter.id) DomainErrorUtils.addError(validatedPayment, "Campo Id vazio")

    }

    private void validateCommonData(BasePaymentAdapter adapter, Payment validatedPayment) {
        Payer payer = Payer.get(adapter.payerId)

        if (!adapter.payerId) DomainErrorUtils.addError(validatedPayment, "Campo payerId vazio")

        if (!payer || payer.deleted) DomainErrorUtils.addError(validatedPayment, "Pagador Inválido")

        if (!adapter.value) DomainErrorUtils.addError(validatedPayment, "Campo valor vazio")

        if (adapter.value <= 0) DomainErrorUtils.addError(validatedPayment, "Valor inválido")

        if (!adapter.description) DomainErrorUtils.addError(validatedPayment, "Campo descrição vazio")

        if (!adapter.billingType) DomainErrorUtils.addError(validatedPayment, "Campo tipo de pagamento vazio")

        if (!adapter.dueDate) DomainErrorUtils.addError(validatedPayment, "Campo data de vencimento vazio")

        if (adapter.dueDate < new Date()) DomainErrorUtils.addError(validatedPayment, "A data de vencimento deve ser posterior à data atual")

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
