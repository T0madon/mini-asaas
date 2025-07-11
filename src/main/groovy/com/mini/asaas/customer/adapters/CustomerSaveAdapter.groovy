package com.mini.asaas.customer.adapters

import com.mini.asaas.enums.PersonType
import com.mini.asaas.utils.StringUtils
import com.mini.asaas.utils.Utils
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CustomerSaveAdapter {

    String name

    String cpfCnpj

    String postalCode

    String state

    String city

    String neighborhood

    String address

    String addressNumber

    String complement

    PersonType personType

    public CustomerSaveAdapter(Map originalParams) {
        if (!originalParams) return

        if (originalParams.personTypeString == 'NATURAL') {
            originalParams.cpfCnpj = originalParams.cpf
        } else {
            originalParams.cpfCnpj = originalParams.cnpj
        }       
         
        Map<String, String> params = Utils.normalizeParams(originalParams)
        
        if (!params) return

        this.name = params.name
        this.cpfCnpj = StringUtils.removeNonNumeric(params.cpfCnpj)
        this.postalCode = StringUtils.removeNonNumeric(params.postalCode)
        this.state = params.state?.toUpperCase()
        this.city = params.city
        this.neighborhood = params.neighborhood
        this.address = params.address
        this.addressNumber = params.addressNumber ?: "S/N"
        this.complement = params.complement
        this.personType = PersonType.parseFromCpfCnpj(this.cpfCnpj)
    }
}