package com.mini.asaas.payer

import com.mini.asaas.BaseController
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import grails.plugin.springsecurity.annotation.Secured

class PayerController extends BaseController{

    PayerService payerService

    def index() {
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
            createFlash("Cadastro realizado!", AlertType.SUCCESS, true)
            render(status: 201, contentType: 'application/json')
        } catch (Exception exception) {
            createFlash("Ocorreu um erro no cadastro!", AlertType.ERROR, false)
        }
    }

    @Secured("permitAll")
    def update() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            Long id = params.id as Long

            payerService.update(adapter, id)
            createFlash("Pagador atualizado com sucesso!", AlertType.SUCCESS, true)
            render(status: 201, contentType: 'application/json')
        } catch (BusinessException error) {
            createFlash(error.getMessage(), AlertType.ERROR, false)
        } catch (Exception error) {
            createFlash("Ocorreu um erro durante o cadastro.", AlertType.ERROR, false)
        }
    }

    @Secured("permitAll")
    def delete() {
        try {
            Long id = params.id as Long

            if (!id) return
            payerService.delete(id)
            createFlash("Pagador deletado!", AlertType.SUCCESS, true)
            render(status: 200, contentType: 'application/json')

        } catch (Exception exception) {
            createFlash("Ocorreu um erro durante o delete, tente novamente.", AlertType.ERROR, false)
        }
    }
}
