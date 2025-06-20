package com.mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Utils {

    public static Map<String, String> normalizeParams(Map params) {
        if (params == null) return [:]

        Map<String, String> cleanedParams = [:]

        for (String key : params.keySet()) {
            cleanedParams[key] = StringUtils.ensureStringAndTrim(params[key])
        }

        return cleanedParams
    }
}