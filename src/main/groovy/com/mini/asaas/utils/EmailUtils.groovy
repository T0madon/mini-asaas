package com.mini.asaas.utils

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class EmailUtils {

    private static final Integer EMAIL_LOCAL_PART_INDEX = 0
    private static final Integer EMAIL_DOMAIN_INDEX = 1
    private static final String EMAIL_SEPARATOR = "@"
    private static final String EMAIL_REGEX = /^[a-zA-Z0-9_!#$%&'*+\/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$/

    public static Boolean isValid(String email) {
        return email && email.matches(EMAIL_REGEX)
    }

    public static String getDomain(String email) {
        return getPart(email, EMAIL_DOMAIN_INDEX)
    }

    public static String getLocalPart(String email) {
        return getPart(email, EMAIL_LOCAL_PART_INDEX)
    }

    private static String getPart(String email, Integer index) {
        if (!isValid(email) || !isValidIndex(index)) return null
        String[] parts = email.split(EMAIL_SEPARATOR)
        if (parts.size() != 2) return null
        return parts[index] ?: null
    }

    private static Boolean isValidIndex(Integer index) {
        return index != null && [EMAIL_LOCAL_PART_INDEX, EMAIL_DOMAIN_INDEX].contains(index)
    }
}
