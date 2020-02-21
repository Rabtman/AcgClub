package com.rabtman.acgmusic.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


/**
 * @author Rabtman
 * 音乐数据类
 */

data class MusicInfo(
        @SerializedName("code")
        var code: Int = -1,
        @SerializedName("msg")
        var msg: String = "unknown",
        @SerializedName("res")
        var res: Res = Res()
) : Parcelable {
    override fun toString(): String {
        return "MusicInfo(code=$code, msg='$msg', res=$res)"
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readParcelable<Res>(Res::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(code)
        writeString(msg)
        writeParcelable(res, 0)
    }

    fun readFromParcel(dest: Parcel) {
        code = dest.readInt()
        msg = dest.readString()
        res = dest.readParcelable<Res>(Res::class.java.classLoader)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MusicInfo> = object : Parcelable.Creator<MusicInfo> {
            override fun createFromParcel(source: Parcel): MusicInfo = MusicInfo(source)
            override fun newArray(size: Int): Array<MusicInfo?> = arrayOfNulls(size)
        }
    }
}

data class Res(
        @SerializedName("anime_info")
        var animeInfo: AnimeInfo = AnimeInfo(),
        @SerializedName("atime")
        var atime: Int = 0,
        @SerializedName("author")
        var author: String = "",
        @SerializedName("id")
        var id: String = "",
        @SerializedName("play_url")
        var playUrl: String = "",
        @SerializedName("recommend")
        var recommend: Boolean = false,
        @SerializedName("title")
        var title: String = "",
        @SerializedName("type")
        var type: String = ""
) : Parcelable {
    override fun toString(): String {
        return "Res(animeInfo=$animeInfo, atime=$atime, author='$author', id='$id', playUrl='$playUrl', recommend=$recommend, title='$title', type='$type')"
    }

    constructor(source: Parcel) : this(
            source.readParcelable<AnimeInfo>(AnimeInfo::class.java.classLoader),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(animeInfo, 0)
        writeInt(atime)
        writeString(author)
        writeString(id)
        writeString(playUrl)
        writeInt((if (recommend) 1 else 0))
        writeString(title)
        writeString(type)
    }

    fun readFromParcel(dest: Parcel) {
        animeInfo = dest.readParcelable<AnimeInfo>(AnimeInfo::class.java.classLoader)
        atime = dest.readInt()
        author = dest.readString()
        id = dest.readString()
        playUrl = dest.readString()
        recommend = 1 == dest.readInt()
        title = dest.readString()
        type = dest.readString()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Res> = object : Parcelable.Creator<Res> {
            override fun createFromParcel(source: Parcel): Res = Res(source)
            override fun newArray(size: Int): Array<Res?> = arrayOfNulls(size)
        }
    }
}

data class AnimeInfo(
        @SerializedName("atime")
        var atime: Int = 0,
        @SerializedName("bg")
        var bg: String = "",
        @SerializedName("desc")
        var desc: String = "",
        @SerializedName("id")
        var id: String = "",
        @SerializedName("logo")
        var logo: String = "",
        @SerializedName("month")
        var month: Int = 0,
        @SerializedName("title")
        var title: String = "",
        @SerializedName("year")
        var year: Int = 0
) : Parcelable {
    override fun toString(): String {
        return "AnimeInfo(atime=$atime, bg='$bg', desc='$desc', id='$id', logo='$logo', month=$month, title='$title', year=$year)"
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(atime)
        writeString(bg)
        writeString(desc)
        writeString(id)
        writeString(logo)
        writeInt(month)
        writeString(title)
        writeInt(year)
    }

    fun readFromParcel(dest: Parcel) {
        atime = dest.readInt()
        bg = dest.readString()
        desc = dest.readString()
        id = dest.readString()
        logo = dest.readString()
        month = dest.readInt()
        title = dest.readString()
        year = dest.readInt()
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AnimeInfo> = object : Parcelable.Creator<AnimeInfo> {
            override fun createFromParcel(source: Parcel): AnimeInfo = AnimeInfo(source)
            override fun newArray(size: Int): Array<AnimeInfo?> = arrayOfNulls(size)
        }
    }
}