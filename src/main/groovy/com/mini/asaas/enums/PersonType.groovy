package com.mini.asaas.enums

import com.mini.asaas.utils.CpfCnpjUtils

enum PersonType {
    LEGAL,
    NATURAL

    public static PersonType parseFromCpfCnpj(String cpfCnpj) {
        if (CpfCnpjUtils.isCPF(cpfCnpj)) return NATURAL
        if (CpfCnpjUtils.isCNPJ(cpfCnpj)) return LEGAL
        return null
    }
}