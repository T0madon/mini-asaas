package com.mini.asaas.enums

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
enum AlertType {
    ERROR,
    INFO,
    SUCCESS,
    WARNING

    public String getValue() {
        return this.name().toLowerCase();
    }
}