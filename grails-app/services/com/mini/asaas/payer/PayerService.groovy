package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.PersonType
import com.mini.asaas.utils.StringUtils
import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    public Payer save(PayerAdapter adapter, Customer customer) {
        Payer payer = new Payer()
        payer.customer = customer

        if (!customer) throw new NoSuchElementException("Customer não encontrado!")

        payer = buildPayer(adapter, payer)
        payer.save(failOnError: true)

        return payer
    }

    private Payer buildPayer(PayerAdapter adapter, Payer payer) {
        payer.name = adapter.name
        payer.email = adapter.email
        payer.cpfCnpj = adapter.cpfCnpj
        payer.postalCode = adapter.postalCode
        payer.state = adapter.state
        payer.city = adapter.city
        payer.neighborhood = adapter.neighborhood
        payer.address = adapter.address
        payer.addressNumber = adapter.addressNumber
        payer.complement = adapter.complement
        payer.personType = adapter.personType
    }
}
