package com.mini.asaas.payer

import com.mini.asaas.customer.Customer
import com.mini.asaas.utils.CpfCnpjUtils
import com.mini.asaas.utils.DomainErrorUtils
import com.mini.asaas.utils.EmailUtils
import com.mini.asaas.utils.StringUtils
import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

import javax.xml.bind.ValidationException

@Transactional
@GrailsCompileStatic
class PayerService {

    public Payer save(PayerAdapter adapter) {
        println("Entrei para salvar")
        Payer payer = new Payer()
        Customer customer = Customer.get(1)
        println("Vou validar")
        validate(customer.id, adapter, payer)
        println("Validei")

        if (payer.hasErrors()) {
            List<String> errorMessages = payer.errors.allErrors.collect { it.defaultMessage }
            throw new ValidationException(" Falha ao cadastrar pagador: " + errorMessages.join("; "))
        }

        buildPayer(adapter, payer)

        payer.customer = customer
        payer.save(failOnError: true)
        return payer
    }

    public Payer findById(Long customerId, Long id) {
        Payer payer = PayerRepository.query([id: id, customerId: customerId]).readOnly().get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")
        return payer
    }

    public Payer update(Long customerId, Long id, PayerAdapter adapter) {
        Payer payer = PayerRepository.query([id: id, customerId: customerId]).get()

        if (!payer) throw new RuntimeException("Pagador não encontrado")

        validate(customerId, adapter, payer)

        if (payer.hasErrors()) {
            List<String> errorMessages = payer.errors.allErrors.collect { it.defaultMessage }
            throw new ValidationException(" Falha ao atualizar pagador: " + errorMessages.join("; "))
        }

        buildPayer(adapter, payer)

        payer.save(failOnError: true)
        return payer
    }

    public void delete(Long customerId, Long id) {
        Payer payer = PayerRepository.query([customerId: customerId, id: id]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.deleted = true
        payer.save(failOnError: true)
    }

    public void restore(Long customerId, Long id) {
        Payer payer = PayerRepository.query([customerId: customerId, id: id, includeDeleted: true]).get()
        if (!payer) throw new RuntimeException("Pagador não encontrado")

        payer.deleted = false
        payer.save(failOnError: true)
    }

    public List<Payer> list(Long customerId) {
        return PayerRepository.query([customerId: customerId]).readOnly().list()
    }

    public List<Payer> list(Long customerId, Integer max, Integer offset) {
        return PayerRepository.query([customerId: customerId]).readOnly().list([max: max, offset: offset])
    }

    public List<Payer> listDeleted(Long customerId, Integer max, Integer offset) {
        return PayerRepository.query([customerId: customerId, deletedOnly: true]).readOnly().list([max: max, offset: offset])
    }

    private void validate(Long customerId, PayerAdapter adapter, Payer payer) {

        String searchedCpfCnpj = StringUtils.removeNonNumeric(adapter.cpfCnpj as String) ?: null

        Payer existingWithCpfCnpj = PayerRepository.query([
                customerId: customerId,
                cpfCnpj: searchedCpfCnpj
        ]).readOnly().get()

        Payer existingWithEmail = PayerRepository.query([
                customerId: customerId,
                email: adapter.email
        ]).readOnly().get()

        if (existingWithCpfCnpj && existingWithCpfCnpj.id != payer.id) {
            DomainErrorUtils.addError(payer, "O cpf/cnpj informado já existe")
        }

        if (existingWithEmail && existingWithEmail.id != payer.id) {
            DomainErrorUtils.addError(payer, "O email informado já existe")
        }

//        if (PayerRepository.query([customerId: customerId, cpfCnpj: searchedCpfCnpj]).readOnly().get()) {
//            DomainErrorUtils.addError(payer, "O cpf/cnpj informado já existe")
//        }

//        if (PayerRepository.query([customerId: customerId, email: adapter.email]).readOnly().get()) {
//            DomainErrorUtils.addError(payer, "O email informado já existe")
//        }

        if (!adapter.name) DomainErrorUtils.addError(payer, "Campo nome vazio")

        if (!adapter.email) DomainErrorUtils.addError(payer, "Campo Email vazio")

        if (!adapter.cpfCnpj) DomainErrorUtils.addError(payer, "O Cpf/Cnpj é obrigatório")

        if (adapter.cpfCnpj && !CpfCnpjUtils.isValidCpfCnpj(adapter.cpfCnpj)) DomainErrorUtils.addError(payer, "O CPF/CNPJ informado é inválido")

        if (adapter.email && !EmailUtils.isValid(adapter.email)) DomainErrorUtils.addError(payer, "O email informado é inválido")

    }

    private void buildPayer(PayerAdapter adapter, Payer payer) {
        payer.name = adapter.name
        payer.email = adapter.email
        payer.cpfCnpj = StringUtils.removeNonNumeric(adapter.cpfCnpj as String) ?: null
        payer.phoneNumber = StringUtils.removeNonNumeric(adapter.phoneNumber) ?: null
        payer.postalCode = StringUtils.removeNonNumeric(adapter.postalCode) ?: null
        payer.state = adapter.state
        payer.city = adapter.city
        payer.neighborhood = adapter.neighborhood
        payer.address = adapter.address
        payer.addressNumber = StringUtils.removeNonNumeric(adapter.addressNumber) ?: "S/N"
        payer.complement = adapter.complement
        payer.personType = adapter.personType
    }
}
