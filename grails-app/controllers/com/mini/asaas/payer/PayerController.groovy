package com.mini.asaas.payer

import com.mini.asaas.BaseController
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import grails.plugin.springsecurity.annotation.Secured

class PayerController extends BaseController {

    PayerService payerService

    @Secured("permitAll")
    def index() {
        List<Payer> payerList = payerService.list()
        return [payerList: payerList]
    }

    @Secured("permitAll")
    def create() {}

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
            Payer payer = payerService.save(adapter)
            createFlash("Cadastro realizado!", AlertType.SUCCESS, true)
            redirect(action: "show", id: payer.id)
        } catch (BusinessException exception) {
            createFlash("Ocorreu um erro no cadastro!" + exception.getMessage(), AlertType.ERROR, false)
            render view: "create"
        } catch (Exception exception) {
            createFlash("Ocorreu um erro no cadastro!" + exception.getMessage(), AlertType.ERROR, false)
            render view: "create"
        }
    }

    @Secured("permitAll")
    def update() {
        try {
            PayerAdapter adapter = new PayerAdapter(params)
            Long id = params.id as Long

            Payer payer = payerService.update(adapter, id)
            createFlash("Pagador atualizado com sucesso!", AlertType.SUCCESS, true)
            redirect(action: "show", id: payer.id)
        } catch (BusinessException error) {
            createFlash(error.getMessage(), AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        } catch (Exception error) {
            createFlash("Ocorreu um erro durante o cadastro.", AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        }
    }

    @Secured("permitAll")
    def delete() {
        try {
            Long id = params.id as Long

            if (!id) return
            payerService.delete(id)
            createFlash("Pagador deletado!", AlertType.SUCCESS, true)
            redirect(action: "show", id: id)
        } catch (Exception exception) {
            createFlash("Ocorreu um erro durante o delete, tente novamente.", AlertType.ERROR, false)
            redirect(action: "index")
        }
    }
}
