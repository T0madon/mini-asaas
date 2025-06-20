package com.mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Utils {

    public static Map<String, String> normalizeParams(Map params) {
        println("Entrei no normalizeParams")
        if (params == null) return [:]
        println("Aqui 1")
        Map<String, String> cleanedParams = [:]
        println("Aqui 2")
        for (String key : params.keySet()) {
            cleanedParams[key] = StringUtils.ensureStringAndTrim(params[key])
        }
        println("Aqui 3")
        println(cleanedParams)
        return cleanedParams
    }
}