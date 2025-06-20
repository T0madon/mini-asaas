package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.utils.DomainErrorUtils
import com.mini.asaas.utils.StringUtils
import com.mini.asaas.validation.BusinessValidation
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class PayerService {

    public Payer save(PayerAdapter adapter) {
        BusinessValidation validation

        Payer payer = new Payer()
        Customer customer = Customer.get(1)

        validate(adapter, payer, customer)

        if (payer.hasErrors()) throw new BusinessException(DomainErrorUtils.getFirstValidationMessage(payer), validation.getFirstErrorCode())

        buildPayer(adapter, payer)

        payer.customer = customer
        payer.save(failOnError: true)

        return payer
    }

    public Payer show(Long id) {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        Payer payer = PayerRepository.query([id: id, customerId: customerId]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")
        return payer
    }

    public Payer update(PayerAdapter adapter, Long id) {
        BusinessValidation validation

        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        Payer payer = PayerRepository.query([id: id, customerId: customerId]).get()

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        validate(adapter, payer, payer.customer)

        if (payer.hasErrors()) throw new BusinessException(DomainErrorUtils.getFirstValidationMessage(payer), validation.getFirstErrorCode())

        buildPayer(adapter, payer)

        payer.save(flush: true, failOnError: true)

        return payer
    }

    public void delete(Long id) {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        Payer payer = PayerRepository.query([customerId: customerId, id: id]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.deleted = true
        payer.save(flush: true, failOnError: true)
    }

    private void validate(PayerAdapter adapter, Payer payer, Customer customer) {
        BusinessValidation validation
        PayerValidator validator = new PayerValidator()
        validator.validateAll(adapter, payer, customer)

        validation = validator.validation

        if (!validation.isValid()) {
            DomainErrorUtils.addBusinessRuleErrors(payer, validation.errors)
            throw new Exception()
        }

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
