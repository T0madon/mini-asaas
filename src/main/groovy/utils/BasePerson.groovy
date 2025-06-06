package utils

import enums.PersonType

abstract class BasePerson extends BaseEntity{
    String name

    String email

    String cpfCnpj

    // PERGUNTAR DO CEP
    String cep

    String state

    String city

    String neighborhood

    String address

    String addressNumber

    String complement

    PersonType personType

    static constraints = {
        name blank: false
        email blank: false, email: true
        cpfCnpj blank: false, size: 11..14
        // Campo size só está incluindo os números de cpf(11) e cnpj(14)
        state blank: false
        cep blank: false
        state blank: false
        city blank: false
        neighborhood blank: false
        address blank: false
        addressNumber blank: false
        complement nullable: true
        personType blank: false
    }
    static mappings = {
        email unique: true
        cpfCnpj unique: true
    }
}
