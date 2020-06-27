package com.rabtman.acgnews.mvp.model.entity

/**
 * @author Rabtman ishuhui response
 */
data class SHResponse<T>(
        val errNo: Int = 0,
        val errMsg: String? = null,
        val data: T? = null
)