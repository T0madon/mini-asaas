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
            Payer payer = payerService.save(adapter)
            render(status: 201, contentType: 'application/json')
        } catch (Exception exception) {
            flash.message = "Ocorreu um erro durante o cadastro, tente novamente."
        }
    }

    @Secured("permitAll")
    def delete() {
        try {
            Long id = params.id as Long
            if (!id) return
            payerService.delete(id)
            render(status: 200, contentType: 'application/json')

        } catch (Exception exception) {
            flash.message = "Ocorreu um erro durante o delete, tente novamente."
        }
    }
}
