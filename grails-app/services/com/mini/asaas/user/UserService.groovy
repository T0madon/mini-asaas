package com.mini.asaas.user

import com.mini.asaas.customer.Customer
import com.mini.asaas.user.adapters.UserSaveAdapter
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@GrailsCompileStatic
@Transactional
class UserService {

    public User createUserForCustomer(UserSaveAdapter adapter, Customer customer) {
        
        User user = new User(
            username: adapter.email,
            password: adapter.password,
            customer: customer
        )
        
        user.save(failOnError: true)
        return user
    }
}