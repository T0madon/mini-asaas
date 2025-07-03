package com.mini.asaas.customer

import com.mini.asaas.customer.adapters.CustomerSaveAdapter
import com.mini.asaas.user.adapters.UserSaveAdapter
import com.mini.asaas.user.UserService
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class CustomerService {

    UserService userService 

    public Customer createAccount(CustomerSaveAdapter customerAdapter, UserSaveAdapter userAdapter) {
        Customer customer = buildCustomer(customerAdapter, userAdapter)
        customer.save(failOnError: true)
        
        userService.createUserForCustomer(userAdapter, customer)
        return customer
    }

    private Customer buildCustomer(CustomerSaveAdapter customerAdapter, UserSaveAdapter userAdapter) {
        return new Customer(
            name: customerAdapter.name,
            email: userAdapter.email, 
            cpfCnpj: customerAdapter.cpfCnpj,
            postalCode: customerAdapter.postalCode,
            state: customerAdapter.state,
            city: customerAdapter.city,
            neighborhood: customerAdapter.neighborhood,
            address: customerAdapter.address,
            addressNumber: customerAdapter.addressNumber,
            complement: customerAdapter.complement,
            personType: customerAdapter.personType
        )
    }
}