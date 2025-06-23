package com.mini.asaas.payer

import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import grails.plugin.springsecurity.annotation.Secured

class PayerController {

    PayerService payerService

    def index() {
        List<Payer> payerList = payerService.list()
        return [payerList: payerList]
    }

    @Secured("permitAll")
    def show() {
        try {
            Long id = params.id as Long

            if (!id) return redirect(action: "index")

            Payer payer = payerService.show(id)

            return [payer: payer]
        } catch (Exception exception) {
            redirect(action: "index")
        }
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

    @Secured("permitAll")
    def update() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            Long id = params.id as Long

            payerService.update(adapter, id)
            flash.message = "Pagador atualizado com sucesso"
            flash.status = AlertType.SUCCESS.getValue()
            render(status: 201, contentType: 'application/json')
        } catch (BusinessException error) {
            flash.code = error.code
            flash.message = error.getMessage()
            flash.status = AlertType.ERROR.getValue()
        } catch (Exception error) {
            flash.message = "Ocorreu um erro durante o cadastro."
            flash.status = AlertType.ERROR.getValue()
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
