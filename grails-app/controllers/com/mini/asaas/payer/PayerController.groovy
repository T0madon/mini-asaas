package com.mini.asaas.payer

import com.mini.asaas.utils.StringUtils
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
    def deleteOrRestore() {
        try {
            Long id = params.id as Long

            if (!id) {
                def payerByEmail = Payer.findByEmail(params.email as String)
                if (payerByEmail) id = payerByEmail.id
            }

            if (!id) {
                def payerByCpfCnpj = Payer.findByCpfCnpj(StringUtils.removeNonNumeric(params.cpfCnpj as String))
                if (payerByCpfCnpj) id = payerByCpfCnpj.id
            }

            if (!id) return

            payerService.deleteOrRestore(id)

        } catch (Exception exception) {
            flash.message = "Ocorreu um erro durante o delete, tente novamente."
        }
    }
}
