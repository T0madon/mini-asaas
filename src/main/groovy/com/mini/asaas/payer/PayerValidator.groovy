package com.mini.asaas.payer

import com.mini.asaas.base.BaseValidator
import com.mini.asaas.customer.Customer
import com.mini.asaas.utils.CpfCnpjUtils
import com.mini.asaas.utils.EmailUtils

class PayerValidator extends BaseValidator{

    public PayerValidator validateAll(PayerAdapter adapter, Payer payer, Customer customer) {
        if (adapter.cpfCnpj != payer.cpfCnpj) {
            println("Entrei no if do cnpj")
            println(adapter.cpfCnpj)
            println(payer.cpfCnpj)
            this.validateCpfCnpj(adapter.cpfCnpj, customer)
        }
        if (adapter.email != payer.email) {
            println("entrei no if do email")
            this.validateEmail(adapter.email, customer)
        }

        return this
    }

    public PayerValidator validateCpfCnpj(String cpfCnpj, Customer customer) {
        if (!CpfCnpjUtils.isValidCpfCnpj(cpfCnpj)) {
            println("tem erro")
            validation.addError("invalid.cpfCnpj")
        }
        println(cpfCnpj)
        println(customer.id)

        Payer payer = PayerRepository.query([cpfCnpj: cpfCnpj, customerId: customer.id, includeDeleted: true]).get()

        println("vou printar o payer " + payer)
        if (!payer) return this

        if (payer.deleted) {
            validation.addError("alreadyExistsAndDeleted.cpfCnpj")
        } else {
            validation.addError("alreadyExistsAndView.cpfCnpj")
        }
        println("vou printar o this " + this)
        return this
    }

    public PayerValidator validateEmail(String email, Customer customer) {
        if (!EmailUtils.isValid(email)) {
            validation.addError("invalid.email")
        }

        Payer payer = PayerRepository.query([email: email, customerId: customer.id, includeDeleted: true]).get()

        if (!payer) return this

        if (payer.deleted) {
            validation.addError("alreadyExistsAndDeleted.email")
        } else {
            validation.addError("alreadyExistsAndView.email")
        }

        return this
    }

}
