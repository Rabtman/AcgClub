package com.rabtman.acgcomic.mvp.model.entity

import com.google.gson.annotations.SerializedName


data class QiMiaoChapterContent(
        @SerializedName("errorMessage")
        val errorMessage: ErrorMessage = ErrorMessage(),
        @SerializedName("listImg")
        val listImg: List<String> = listOf()
)

data class ErrorMessage(
        @SerializedName("Code")
        val code: String = "",
        @SerializedName("IsError")
        val isError: Boolean = false,
        @SerializedName("MessageStr")
        val messageStr: String = ""
)