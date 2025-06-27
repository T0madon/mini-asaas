package com.mini.asaas.enums

import com.mini.asaas.utils.CpfCnpjUtils
import com.mini.asaas.utils.MessageSourceUtils

enum PersonType {
    LEGAL,
    NATURAL

    public Boolean isNatural() {
        return this == NATURAL
    }

    public Boolean isLegal() {
        return this == LEGAL
    }

    public static PersonType fromString(String value) {
        try {
            return valueOf(value.toUpperCase())
        } catch (IllegalArgumentException exception) {
            return null
        }
    }

    public String getLabel() {
        return MessageSourceUtils.getEnumLabel(this)
    }

    public static PersonType parseFromCpfCnpj(String cpfCnpj) {
        if (CpfCnpjUtils.isCpf(cpfCnpj)) return PersonType.NATURAL
        if (CpfCnpjUtils.isCnpj(cpfCnpj)) return PersonType.LEGAL
        return null
    }
}