package com.mini.asaas.user

import com.mini.asaas.customer.adapters.CustomerAdapter
import com.mini.asaas.customer.CustomerService
import com.mini.asaas.user.adapters.SaveUserAdapter
import grails.compiler.GrailsCompileStatic
import grails.converters.JSON 
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
@Secured(['permitAll'])
class UserController {

    CustomerService customerService

    public def create() {
        render view: 'signUp'
    }

    public def save() {

        def jsonData = request.JSON
        def jsonDataAsMap = jsonData as Map

        def customerAdapter = new CustomerAdapter(jsonDataAsMap)
        def userAdapter = new SaveUserAdapter(jsonDataAsMap)

        try {
            customerService.createAccount(customerAdapter, userAdapter)
            render(status: 201, contentType: 'application/json') {
                [status: "SUCCESS", message: "Conta criada com sucesso!"]
            }

        } catch (Exception e) {
            render(status: 400, contentType: 'application/json') {
                [status: "ERROR", message: "Erro no cadastro: ${e.message}"]
            }
        }
    }
}