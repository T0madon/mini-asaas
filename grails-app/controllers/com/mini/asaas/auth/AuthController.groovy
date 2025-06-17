package com.mini.asaas.auth

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class AuthController {

    AuthService authService
    static allowedMethods = [login: 'GET', authenticate: 'POST']

    def login() {
        render view: 'login'
    }

    def authenticate() {
        try {
            User user = authService.authenticate(params.email, params.password)
            session.user = user
            redirect controller: 'customer', action: 'show', id: user.customer.id
        } catch (AuthException e) {
            flash.message = e.message
            flash.email = params.email
            redirect action: 'login'
        }
    }
}
