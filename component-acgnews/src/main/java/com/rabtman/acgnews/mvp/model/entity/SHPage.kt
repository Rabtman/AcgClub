package com.rabtman.acgnews.mvp.model.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Rabtman ishuhui post page
 */
data class SHPage(
        var count: Int = 0,
        var totalPages: Int = 0,
        @SerializedName("pageSize")
        var numsPerPage: Int = 0,
        var currentPage: Int = 0,
        @SerializedName("data")
        var postItems: List<SHPostItem>? = null
)