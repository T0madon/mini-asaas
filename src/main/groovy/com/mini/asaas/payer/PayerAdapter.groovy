package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.PersonType
import com.mini.asaas.utils.StringUtils
import com.mini.asaas.utils.Utils

class PayerAdapter {

    String name

    String email

    String cpfCnpj

    String postalCode

    String state

    String city

    String neighborhood

    String address

    String addressNumber

    String complement

    PersonType personType

    public PayerAdapter(Map reqParams) {
        Map<String, String> params = Utils.normalizeParams(reqParams)
        if (!params) return

        this.name = params.name
        this.email = params.email
        this.cpfCnpj = params.cpfCnpj
        this.postalCode = params.postalCode
        this.state = params.state
        this.city = params.city
        this.neighborhood = params.neighborhood
        this.address = params.address
        this.addressNumber = params.addressNumber
        this.complement = params.complement
        this.personType = params.personType
    }
}
