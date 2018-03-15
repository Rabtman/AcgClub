package com.rabtman.acgcomic.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * @author Rabtman
 */
open class OacgComicItem(
        @PrimaryKey @SerializedName("id") var id: String = "",
        @SerializedName("comic_name") var comicName: String = "",
        @SerializedName("comic_pic_240_320") var comicPicUrl: String = "",
        @SerializedName("comic_desc") var comicDesc: String = "",
        @SerializedName("comic_tag_name") var comicTagName: String = "",
        @SerializedName("painter_user_nickname") var painterUserNickname: String = "",
        @SerializedName("script_user_nickname") var scriptUserNickname: String = "",
        @SerializedName("click_score") var clickScore: String = "",
        @SerializedName("comic_last_orderidx") var comicLastOrderidx: String = "",
        @SerializedName("comic_theme_id_1") var comicThemeId1: String = "",
        @SerializedName("comic_theme_id_2") var comicThemeId2: String = "",
        @SerializedName("comic_theme_id_3") var comicThemeId3: String = ""
) : RealmObject(), Parcelable {

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(comicName)
        writeString(comicPicUrl)
        writeString(comicDesc)
        writeString(comicTagName)
        writeString(painterUserNickname)
        writeString(scriptUserNickname)
        writeString(clickScore)
        writeString(comicLastOrderidx)
        writeString(comicThemeId1)
        writeString(comicThemeId2)
        writeString(comicThemeId3)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OacgComicItem> = object : Parcelable.Creator<OacgComicItem> {
            override fun createFromParcel(source: Parcel): OacgComicItem = OacgComicItem(source)
            override fun newArray(size: Int): Array<OacgComicItem?> = arrayOfNulls(size)
        }
    }
}