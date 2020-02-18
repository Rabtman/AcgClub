package com.rabtman.acgmusic.mvp.model.entity

import com.google.gson.annotations.SerializedName


/**
 * @author Rabtman
 * 音乐数据类
 */

data class MusicInfo(
        @SerializedName("code")
        val code: Int = -1,
        @SerializedName("msg")
        val msg: String = "unknown",
        @SerializedName("res")
        val res: Res = Res()
) {
    override fun toString(): String {
        return "MusicInfo(code=$code, msg='$msg', res=$res)"
    }
}

data class Res(
        @SerializedName("anime_info")
        val animeInfo: AnimeInfo = AnimeInfo(),
        @SerializedName("atime")
        val atime: Int = 0,
        @SerializedName("author")
        val author: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("play_url")
        val playUrl: String = "",
        @SerializedName("recommend")
        val recommend: Boolean = false,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("type")
        val type: String = ""
) {
    override fun toString(): String {
        return "Res(animeInfo=$animeInfo, atime=$atime, author='$author', id='$id', playUrl='$playUrl', recommend=$recommend, title='$title', type='$type')"
    }
}

data class AnimeInfo(
        @SerializedName("atime")
        val atime: Int = 0,
        @SerializedName("bg")
        val bg: String = "",
        @SerializedName("desc")
        val desc: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("logo")
        val logo: String = "",
        @SerializedName("month")
        val month: Int = 0,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("year")
        val year: Int = 0
) {
    override fun toString(): String {
        return "AnimeInfo(atime=$atime, bg='$bg', desc='$desc', id='$id', logo='$logo', month=$month, title='$title', year=$year)"
    }
}