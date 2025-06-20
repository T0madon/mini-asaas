package com.mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class CpfCnpjUtils {

    public static final Integer CPF_LENGTH = 11
    public static final Integer CNPJ_LENGTH = 14

    public static Boolean isCpf(String cpfCnpj) {
        if (cpfCnpj == null) return false

        String savedCpfCnpj = StringUtils.removeNonNumeric(cpfCnpj)
        return savedCpfCnpj.length() == CPF_LENGTH
    }

    public static Boolean isCnpj(String cpfCnpj) {
        if (cpfCnpj == null) return false

        String savedCpfCnpj = StringUtils.removeNonNumeric(cpfCnpj)
        return savedCpfCnpj.length() == CNPJ_LENGTH
    }

}