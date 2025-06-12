package com.mini.asaas.base

abstract class BaseEntity {

    Date dateCreated

    Date lastUpdated

    Boolean deleted = false

    static mapping = {
        tablePerHierarchy false
    }
}
