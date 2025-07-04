package com.mini.asaas.base

import com.mini.asaas.enums.PersonType
import grails.gorm.dirty.checking.DirtyCheck

@DirtyCheck
abstract class BasePerson extends BaseEntity {

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

    static constraints = {
        name blank: false
        email blank: false, email: true
        cpfCnpj blank: false, size: 11..14
        state blank: false
        postalCode blank: false
        state blank: false
        city blank: false
        neighborhood blank: false
        address blank: false
        addressNumber blank: false
        complement nullable: true
        personType blank: false
    }

    static mappings = {
        email unique: true
        cpfCnpj unique: true
    }
}
