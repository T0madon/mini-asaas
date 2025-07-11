package com.mini.asaas.payer

import com.mini.asaas.BaseController
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.enums.AlertType
import grails.gorm.PagedResultList
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

class PayerController extends BaseController {

    PayerService payerService

    @Secured("permitAll")
    def index() {
        try {
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            Integer limitPage = getDefaultLimitPerPage()
            Integer offsetPage = getOffset()

            PagedResultList<Payer> payerList = payerService.list(customerId, limitPage, offsetPage)
            Long total = payerList.getTotalCount()

            return [payerList: payerList, total: total, limitPage: limitPage]
        } catch (Exception exception) {
            throw new RuntimeException("Exception: " + exception)
        }
    }

    @Secured("permitAll")
    def create() {}

    @Secured("permitAll")
    def showDeleted() {
        try {
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            Integer limitPage = getDefaultLimitPerPage()
            Integer offsetPage = getOffset()
            Long total = PayerRepository.query([customerId:customerId, deletedOnly: true]).readOnly().count()

            List<Payer> payerList = payerService.listDeleted(customerId, limitPage, offsetPage)
            return [payerList: payerList, total: total, limitPage: limitPage]
        } catch (Exception exception) {
            throw new RuntimeException("Excpetion: " + exception)
            redirect(action: "index")
        }
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
        } catch (ValidationException validationException) {
            createFlash(validationException.errors.allErrors.defaultMessage.join("; "), AlertType.ERROR, false)
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
        } catch (ValidationException validationException) {
            createFlash(validationException.errors.allErrors.defaultMessage.join("; "), AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        } catch (Exception exception) {
            createFlash("Ocorreu um erro ao atualizar!" + exception.getMessage(), AlertType.ERROR, false)
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
    def restore() {
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
