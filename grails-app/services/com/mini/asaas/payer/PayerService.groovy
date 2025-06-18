package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.utils.DomainErrorUtils
import com.mini.asaas.utils.StringUtils
import com.mini.asaas.validation.BusinessValidation
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional
import grails.validation.ValidationException

@Transactional
@GrailsCompileStatic
class PayerService {

    BusinessValidation validation

    public Payer save(PayerAdapter adapter) {
        Payer payer = new Payer()
        Customer customer = Customer.get(1)

        validate(adapter, payer, customer)

        if (payer.hasErrors()) throw new BusinessException(DomainErrorUtils.getFirstValidationMessage(payer), validation.getFirstErrorCode())

        buildPayer(adapter, payer)

        payer.customer = customer
        payer.save(failOnError: true)

        return payer
    }

    public Payer update(PayerAdapter adapter, Long id) {
//        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
//        Payer payer = PayerRepository.query([id: id, customerId: customerId]).get()
        Payer payer = Payer.get(id)

        println("Entrei no PayerService Update\nA seguir o payer encontrado com id " + id)
        println("\nOLD PAYER ANTES DO VALIDATE\n")

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.properties.each {key, value ->
            println("Atributo: $key, Valor: $value")
        }

        println("Vou validar")
//        validate(adapter, payer, payer.customer)

        println("Validei")
//        if (payer.hasErrors()) throw new BusinessException(DomainErrorUtils.getFirstValidationMessage(payer), validation.getFirstErrorCode())

        println("Vou dar um buildPayer")
        println("AGORA OS ATRIBUTOS NOVOS:\n")
        println("Adapter State: $adapter.state \nAdapter City: $adapter.city\nAdapter Neighborhood: $adapter.neighborhood")
        buildPayer(adapter, payer)
//        payer.state = adapter.state
//        payer.city = adapter.city
//        payer.neighborhood = adapter.neighborhood
//
//        payer.email = "alguma_coisa@email.com"
//        payer.cpfCnpj = "12345678900"

        println("\nNOVO PAYER\n\n")
        payer.properties.each {key, value ->
            println("Atributo: $key, Valor: $value")
        }

        payer.save(flush: true, failOnError: true)
        println("Payer atualizado")

        return payer

//        try {
////            payer.markDirty()
//            payer.save(flush: true, failOnError: true)
//            println("Payer atualizado")
//        } catch (ValidationException e){
//            payer.errors.allErrors.each { error ->
//                println(" - Erro ${error}")
//            }
//        } catch (Exception e) {
//            println("Ocorreu outroo tipo de erro: ${e.message}")
//            e.printStackTrace()
//        }

    }

    public void delete(Long id) {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        Payer payer = PayerRepository.query([id: id, includeDeleted: true, customerId: customerId]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.deleted = true
//        payer.markDirty()
        payer.save(flush: true, failOnError: true)
    }

    private void validate(PayerAdapter adapter, Payer payer, Customer customer) {
        PayerValidator validator = new PayerValidator()
        print("entrei no \n")
        validator.validateAll(adapter, payer, customer)

        validation = validator.validation

        if (!validation.isValid()) {
            println("Entrou aqui no validate")
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
