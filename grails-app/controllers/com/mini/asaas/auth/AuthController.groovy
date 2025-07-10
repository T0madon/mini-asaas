package com.mini.asaas.auth

import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.user.adapters.UserLoginAdapter
import grails.compiler.GrailsCompileStatic
import grails.plugin.springsecurity.annotation.Secured

@GrailsCompileStatic
class AuthController {

    AuthService authService

    @Secured(['permitAll'])
    def login() {
        render view: 'login'
    }

    @Secured(['permitAll'])
    def authenticate() {
        try {
            authService.login(new UserLoginAdapter(params))
            redirect (controller: "payer", action: "index")

        } catch (BusinessException exception) {
            flash.message = exception.message
            flash.type = AlertType.ERROR
            redirect action: 'login'
        } catch (Exception exception) {
            exception.printStackTrace()
            flash.message = "Erro ao autenticar"
            flash.status = AlertType.ERROR
            redirect action: 'login'    
        }
    }
}