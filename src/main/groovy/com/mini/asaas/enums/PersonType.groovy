package com.mini.asaas.enums

import com.mini.asaas.utils.CpfCnpjUtils

enum PersonType {
    LEGAL,
    NATURAL

    public static PersonType fromString(String value) {
        try {
            print('estou dentro do FromString')
            print(valueOf(value.toUpperCase()))
            return valueOf(value.toUpperCase())
        } catch (Exception ignored) {
            return null
        }
    }

    public static PersonType parseFromCpfCnpj(String cpfCnpj) {
        if (CpfCnpjUtils.isCPF(cpfCnpj)) return NATURAL
        if (CpfCnpjUtils.isCNPJ(cpfCnpj)) return LEGAL
        return null
    }
}