package com.rabtman.common.http

/**
 * @author Rabtman
 */
class ApiException(msg: String?, code: Int = 0) : Exception(msg) {
    var code = 0

    init {
        this.code = code
    }
}