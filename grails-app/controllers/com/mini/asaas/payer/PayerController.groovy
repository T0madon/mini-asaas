package com.mini.asaas.payer

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class PayerController {

    def payerService

    def index() {
    }

    @Secured("permitAll")
    def save() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            println('Passou do adapter')
            Payer payer = payerService.save(adapter)
            println('passou do service')
            render(status: 201, contentType: 'application/json')
        } catch (Exception e) {
            flash.message = "Ocorreu um erro durante o cadastro, tente novamente."
        }
    }
}
