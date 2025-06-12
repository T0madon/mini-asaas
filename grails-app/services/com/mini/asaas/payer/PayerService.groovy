package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.PersonType
import com.mini.asaas.utils.StringUtils
import grails.gorm.transactions.Transactional

@Transactional
class PayerService {

    public Payer save(PayerAdapter adapter) {
        Payer payer = new Payer()
        payer.customer = Customer.get(1)

        println(payer.customer)
        println('vai entrar no build com payer:')
        println(payer)

        payer = buildPayer(adapter, payer)

        println(payer)

        payer.save(failOnError: true)

        println('Aqui é para estar salvo')
        println(payer)

        return payer
    }

    private Payer buildPayer(PayerAdapter adapter, Payer payer) {
        println('entrei no buildPayer')
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
