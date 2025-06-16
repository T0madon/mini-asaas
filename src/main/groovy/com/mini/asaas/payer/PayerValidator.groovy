package com.mini.asaas.payer

import com.mini.asaas.base.BaseValidator
import com.mini.asaas.customer.Customer
import com.mini.asaas.utils.CpfCnpjUtils
import com.mini.asaas.utils.EmailUtils

class PayerValidator extends BaseValidator{

    public PayerValidator validateAll(PayerAdapter adapter, Payer payer, Customer customer) {
        if (adapter.cpfCnpj != payer.cpfCnpj) {
            this.validateCpfCnpj(adapter.cpfCnpj, customer)
        }
        if (adapter.email != payer.email) {
            this.validateEmail(adapter.email, customer)
        }

        return this
    }

    public PayerValidator validateCpfCnpj(String cpfCnpj, Customer customer) {
        if (!CpfCnpjUtils.isValidCpfCnpj(cpfCnpj)) {
            validation.addError("invalid.cpfCnpj")
        }

        Payer payer = PayerRepository.query([cpfCnpj: cpfCnpj, customerId: customer.id, includeDeleted: true]).get()

        if (!payer) return this

        if (payer.deleted) {
            validation.addError("alreadyExistsAndDeleted.cpfCnpj")
        } else {
            validation.addError("alreadyExistsAndView.cpfCnpj")
        }
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
