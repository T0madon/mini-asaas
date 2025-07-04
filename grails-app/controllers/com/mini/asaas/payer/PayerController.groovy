package com.mini.asaas.payer

import com.mini.asaas.BaseController
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import grails.plugin.springsecurity.annotation.Secured

class PayerController extends BaseController {

    PayerService payerService

    @Secured("permitAll")
    def index() {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()

        Integer limitPage = getDefaultLimitPerPage()
        Integer offsetPage = getOffset()
        Long total = PayerRepository.query([customerId: customerId]).readOnly().count()

        List<Payer> payerList = payerService.list(customerId, limitPage, offsetPage)
        return [payerList: payerList, total: total, limitPage: limitPage]
    }

    @Secured("permitAll")
    def create() {}

    @Secured("permitAll")
    def restore() {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()

        Integer limitPage = getDefaultLimitPerPage()
        Integer offsetPage = getOffset()
        Long total = PayerRepository.query([customerId:customerId, deletedOnly: true]).readOnly().count()

        List<Payer> payerList = payerService.listDeleted(customerId, limitPage, offsetPage)
        return [payerList: payerList, total: total, limitPage: limitPage]
    }

    @Secured("permitAll")
    def show() {
        try {
            Long id = params.id as Long
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            if (!id) return redirect(action: "index")

            Payer payer = payerService.findById(customerId, id)

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
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            Payer payer = payerService.update(customerId, id, adapter)
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
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            if (!id) return
            payerService.delete(customerId, id)
            createFlash("Operação realizada com sucesso!", AlertType.SUCCESS, true)
            redirect(action: "index")
        } catch (Exception exception) {
            createFlash("Ocorreu um erro durante o delete, tente novamente.", AlertType.ERROR, false)
            redirect(action: "index")
        }
    }

    @Secured("permitAll")
    def restored() {
        try {
            Long id = params.id as Long
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            payerService.restore(customerId, id)
            createFlash("Operação realizada com sucesso!", AlertType.SUCCESS, true)
            redirect(action: "index")
        } catch (Exception exception) {
            createFlash("Ocorreu um erro durante a restauração, tente novamente.", AlertType.ERROR, false)
            redirect(action: "index")
        }
    }
}
