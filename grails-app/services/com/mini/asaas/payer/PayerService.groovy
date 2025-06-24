package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.payment.PaymentStatus
import com.mini.asaas.utils.CpfCnpjUtils
import com.mini.asaas.utils.DomainErrorUtils
import com.mini.asaas.utils.EmailUtils
import com.mini.asaas.utils.StringUtils
import com.mini.asaas.validation.BusinessValidation
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
@GrailsCompileStatic
class PayerService {

    public Payer save(PayerAdapter adapter) {
        Payer payer = new Payer()
        Customer customer = Customer.get(1)
        validate(adapter, payer)

        if (payer.hasErrors()) throw new ValidationException("Falha ao salvar novo Pagador", payer.errors as String)

        buildPayer(adapter, payer)

        payer.customer = customer
        payer.save(failOnError: true)
        return payer
    }

    public void delete(Long id) {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        Payer payer = PayerRepository.query([customerId: customerId, id: id]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.deleted = true
        payer.save(failOnError: true)
    }

    private void validate(PayerAdapter adapter, Payer payer) {

        if (!adapter.name) DomainErrorUtils.addError(payer, "Campo nome vazio")

        if (!adapter.email) DomainErrorUtils.addError(payer, "Campo Email vazio")

        if (!adapter.cpfCnpj) DomainErrorUtils.addError(payer, "O Cpf/Cnpj é obrigatório")

        if (adapter.cpfCnpj && !CpfCnpjUtils.isValidCpfCnpj(adapter.cpfCnpj)) DomainErrorUtils.addError(payer, "O CPF/CNPJ informado é inválido")

        if (adapter.email && !EmailUtils.isValid(adapter.email)) DomainErrorUtils.addError(payer, "O email informado é inválido")

    }

    private void buildPayer(PayerAdapter adapter, Payer payer) {
        payer.name = adapter.name
        payer.email = adapter.email
        payer.cpfCnpj = StringUtils.removeNonNumeric(adapter.cpfCnpj as String) ?: null
        payer.postalCode = StringUtils.removeNonNumeric(adapter.postalCode) ?: null
        payer.state = adapter.state
        payer.city = adapter.city
        payer.neighborhood = adapter.neighborhood
        payer.address = adapter.address
        payer.addressNumber = StringUtils.removeNonNumeric(adapter.addressNumber) ?: "S/N"
        payer.complement = adapter.complement
        payer.personType = adapter.personType

    }
}
