package com.mini.asaas.enums

import com.mini.asaas.utils.CpfCnpjUtils

enum PersonType {
    LEGAL,
    NATURAL

    public static PersonType fromString(String value) {
        try {
            return valueOf(value.toUpperCase())
        } catch (IllegalArgumentException exception) {
            return null
        }
    }

    public static PersonType parseFromCpfCnpj(String cpfCnpj) {
        if (CpfCnpjUtils.isCpf(cpfCnpj)) return PersonType.NATURAL
        if (CpfCnpjUtils.isCnpj(cpfCnpj)) return PersonType.LEGAL
        return null
    }
}