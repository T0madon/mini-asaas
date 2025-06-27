package com.mini.asaas.user

import com.mini.asaas.customer.Customer
import com.mini.asaas.enums.PersonType
import com.mini.asaas.user.User
import grails.gorm.transactions.Transactional

@Transactional
class UserService {
    User registerUser(String email, String password,
                     String customerName, String customerCpfCnpj,
                     String customerPostalCode, String customerState, String customerCity,
                     String customerNeighborhood, String customerAddress, String customerAddressNumber,
                     String customerComplement, PersonType personType) {

        def customer = new Customer(
            name: customerName,
            email: email,
            cpfCnpj: customerCpfCnpj,
            postalCode: customerPostalCode,
            state: customerState,
            city: customerCity,
            neighborhood: customerNeighborhood,
            address: customerAddress,
            addressNumber: customerAddressNumber,
            complement: customerComplement,
            personType: personType
        )
        customer.save(flush: true, failOnError: true)

        def user = new User()
        
        user.username = email
        user.password = password
        user.customer = customer
        user.name = customerName
        user.email = email
        user.cpfCnpj = customerCpfCnpj
        user.postalCode = customerPostalCode
        user.state = customerState
        user.city = customerCity
        user.neighborhood = customerNeighborhood
        user.address = customerAddress
        user.addressNumber = customerAddressNumber
        user.complement = customerComplement
        user.personType = personType
        
        user.save(flush: true, failOnError: true)
        println "User [${user.username}] User ID: ${user.id}"

        return user
    }
}