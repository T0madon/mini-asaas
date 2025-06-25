package com.mini.asaas.payment

import com.mini.asaas.Payment.Payment
import com.mini.asaas.customer.Customer
import com.mini.asaas.payer.Payer
import com.mini.asaas.utils.CpfCnpjUtils
import com.mini.asaas.utils.DomainErrorUtils
import com.mini.asaas.utils.EmailUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import org.h2.engine.Domain

import javax.xml.bind.ValidationException

@Transactional
@GrailsCompileStatic
class PaymentService {

    public Payment save(PaymentAdapter adapter) {
        Payment payment = new Payment()
        Customer customer = Customer.get(1)
        validate(adapter, payment)

        if (payment.hasErrors()) throw new ValidationException("Falha ao salvar novo Pagamento", payment.errors as String)

        buildPayment(adapter, payment)

        payment.customer = customer
        payment.save(failOnError: true)
        return payment
    }

    private void validate(PaymentAdapter adapter, Payment validatedPayment) {
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

    private void buildPayment(PaymentAdapter adapter, Payment payment) {
        payment.payer = Payer.get(adapter.payerId)
        payment.billingType = adapter.billingType
        payment.value = adapter.value
        payment.status = adapter.status
        payment.description = adapter.description
        payment.dueDate = adapter.dueDate

    }
}
