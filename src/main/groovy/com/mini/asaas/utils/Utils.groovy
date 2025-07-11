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

    public static Long toLong(value) {
        try {
            if (value instanceof Long) return value
            if (value instanceof BigInteger) return Long.valueOf(value.toString())
            return Long.valueOf(value as String)
        } catch (Exception exception) {
            return null
        }
    }
}