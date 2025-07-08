package com.mini.asaas.user

import com.mini.asaas.customer.CustomerService
import com.mini.asaas.customer.adapters.CustomerSaveAdapter
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.user.adapters.UserSaveAdapter
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class UserController {

    CustomerService customerService

    @Secured(['permitAll'])
    def create() {
        render view: 'signUp'
    }

    @Secred(['permitAll'])
    def save() {
        CustomerSaveAdapter customerAdapter = new CustomerSaveAdapter(params)
        UserSaveAdapter userAdapter = new UserSaveAdapter(params)

        try {
            customerService.createAccount(customerAdapter, userAdapter)

            flash.message = "Conta criada com sucesso! Por favor, faça seu login."
            flash.status = AlertType.SUCCESS.getValue()
            redirect(controller: 'auth', action: 'login')
        }
        
        catch (BusinessException exception) {
            flash.message = exception.message
            flash.status = AlertType.ERROR.getValue()
            redirect(action: 'create', params: params)
        }

        catch (Exception exception) {
            flash.message = "Ocorreu um erro inesperado."
            flash.status = AlertType.ERROR.getValue()
            redirect(action: 'create', params: params)
        }
    }
}