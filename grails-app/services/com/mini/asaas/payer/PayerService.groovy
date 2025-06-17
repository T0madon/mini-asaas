package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.utils.StringUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class PayerService {

    public Payer save(PayerAdapter adapter) {
        Payer payer = new Payer()
        payer.customer = Customer.get(1)
        payer = buildPayer(adapter, payer)

        payer.save(failOnError: true)

        return payer
    }

    public void delete(Long id) {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()
        Payer payer = PayerRepository.query([customerId: customerId, includeDeleted: true, id: id]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.deleted = true
        payer.markDirty()
        payer.save(failOnError: true)
    }

    private Payer buildPayer(PayerAdapter adapter, Payer payer) {
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
        
        return payer
    }
}
