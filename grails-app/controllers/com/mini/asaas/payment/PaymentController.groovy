package com.mini.asaas.payment

import com.mini.asaas.BaseController
import com.mini.asaas.Payment.Payment
import com.mini.asaas.customer.CustomerRepository
import com.mini.asaas.enums.AlertType
import com.mini.asaas.exceptions.BusinessException
import com.mini.asaas.payer.Payer
import com.mini.asaas.payer.PayerService
import grails.plugin.springsecurity.annotation.Secured

class PaymentController extends BaseController{

    PaymentService paymentService

    PayerService payerService

    @Secured("permitAll")
    def index() {
        List<String> statusFilterList = []
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()

        Integer limitPage = getDefaultLimitPerPage()
        Integer offsetPage = getOffset()
        Long total = PaymentRepository.query(customerId: customerId).readOnly().count()

        if (params.status && params.status instanceof String) {
            statusFilterList = (params.status as String).split(",")
        }
        if (statusFilterList.isEmpty()) statusFilterList = PaymentStatus.getAllNames()

        List<Payment> paymentList = paymentService.list(customerId, statusFilterList, limitPage, offsetPage)
        return [paymentList: paymentList, total: total, limitPage: limitPage]
    }

    @Secured("permitAll")
    def create() {
        Long customerId = CustomerRepository.query([id: 1]).column("id").get()

        List<Payer> payerList = payerService.list(customerId)
        return [payerList: payerList]
    }

    @Secured("permitAll")
    def show() {
        try {
            Long id = params.id as Long
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()

            Payment payment = paymentService.findById(customerId, id)

            return [payment: payment]
        } catch (Exception exception) {
            render(status: 400, contentType: 'application/json', text: '{"erro": "Requisição Inválida"}')
        }
    }

    @Secured("permitAll")
    def save() {
        try {
            PaymentSaveAdapter adapter = new PaymentSaveAdapter(params)
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()
            Payment payment = paymentService.save(customerId, adapter)
            createFlash("Cadastro de pagamento realizado!", AlertType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (BusinessException exception) {
            createFlash("Ocorreu um erro no cadastro do pagamento: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "create", params: params)
        } catch (Exception exception) {
            createFlash("Ocorreu um erro no cadastro!" + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "create", params: params)
        }
    }

    @Secured("permitAll")
    def update() {
        try {
            PaymentUpdateAdapter adapter = new PaymentUpdateAdapter(params)
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()
            Payment payment = paymentService.update(customerId, adapter)
            createFlash("Pagamento atualizado com sucesso!", AlertType.SUCCESS, true)
            redirect(action: "show", id: payment.id)
        } catch (BusinessException exception) {
            createFlash("Ocorreu um erro ao atualizar pagamento: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        } catch (Exception exception) {
            createFlash("Ocorreu um erro ao atualizar pagamento: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        }
    }

    @Secured("permitAll")
    def delete() {
        try {
            Long id = params.id as Long
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()
            paymentService.delete(customerId, id)
            createFlash("Pagamento deletado com sucesso!", AlertType.SUCCESS, true)
            redirect(action: "index")
        } catch (BusinessException exception) {
            createFlash("Ocorreu um erro ao deletar: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "index")
        } catch (Exception exception) {
            createFlash("Ocorreu um erro ao deletar: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "index")
        }
    }

    @Secured("permitAll")
    def restore() {
        try {
            Long id = params.id as Long
            Long customerId = CustomerRepository.query([id: 1]).column("id").get()
            paymentService.restore(customerId, id)
            createFlash("Pagamento restaurado com sucesso!", AlertType.SUCCESS, true)
            redirect(action: "index")
        } catch (BusinessException exception) {
            createFlash("Houve um erro: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "index")
        } catch (Exception exception) {
            createFlash("Houve um erro: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "index")
        }
    }

    @Secured("permitAll")
    def receive() {
        Long id = params.id as Long

        try {
            paymentService.receive(id)
            redirect(action: "show", id: params.id)
        } catch (BusinessException exception) {
            createFlash("Houve um erro: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        } catch (Exception exception) {
            log.error(exception)
            createFlash("Houve um erro: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "show", id: params.id)
        }
    }

    @Secured("permitAll")
    def receipt() {
        Long id = params.id as Long

        try {
            Payment payment = PaymentRepository.get(id)
            return [payment: payment]
        } catch (Exception exception) {
            log.error(exception)
            createFlash("Houve um erro ao visualizar o comprovante: " + exception.getMessage(), AlertType.ERROR, false)
            redirect(action: "show", id: id)
        }
    }
}
