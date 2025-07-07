package com.mini.asaas.user

import com.mini.asaas.customer.adapters.CustomerSaveAdapter
import com.mini.asaas.customer.CustomerService
import com.mini.asaas.user.adapters.UserSaveAdapter
import grails.compiler.GrailsCompileStatic
import grails.converters.JSON 
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured(['permitAll'])
class UserController {

    CustomerService customerService

    def save() {
        try {
            def jsonData = request.JSON
            Map jsonDataAsMap = jsonData as Map
            CustomerSaveAdapter customerAdapter = new CustomerSaveAdapter(jsonDataAsMap)
            UserSaveAdapter userAdapter = new UserSaveAdapter(jsonDataAsMap)

            customerService.createAccount(customerAdapter, userAdapter)
            render(status: 201, contentType: 'application/json') {
                [status: "SUCCESS", message: "Conta criada com sucesso!"]
            }

        } catch (Exception exception) {
            render(status: 400, contentType: 'application/json') {
                [status: "ERROR", message: "Erro no cadastro: ${exception.message}"]
            }
        }
    }
}