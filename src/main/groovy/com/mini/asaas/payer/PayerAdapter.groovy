package com.mini.asaas.payer

import com.mini.asaas.enums.PersonType
import com.mini.asaas.utils.Utils
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
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
        println("Sai do normalize")
        println(params)
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
        println("To aqui")
        println(params.personType)
        this.personType = PersonType.parseFromCpfCnpj(this.cpfCnpj)
        println("Vou sair do adapter")
    }
}
