package com.mini.asaas.payer

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class PayerController {

    payerService

    def index() {
    }

    @Secured("permitAll")
    def save() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            Payer payer = payerService.save(adapter)
            render(status: 201, contentType: 'application/json')
        } catch (Exception e) {
            flash.message = "Ocorreu um erro durante o cadastro, tente novamente."
        }
    }
}
