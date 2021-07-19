package com.rabtman.common.http

/**
 * @author Rabtman
 */
class BaseResponse<T> {
    var message: String? = null

    @JvmField
    var data: T? = null
}