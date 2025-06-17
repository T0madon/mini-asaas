package com.mini.asaas.payer

import grails.plugin.springsecurity.annotation.Secured

@Secured("permitAll")
class PayerController {

    PayerService payerService

    def index() {
    }

    @Secured("permitAll")
    def save() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            payerService.save(adapter)
            render(status: 201, contentType: 'application/json')
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro durante o cadastro, tente novamente."
        }
    }
}
