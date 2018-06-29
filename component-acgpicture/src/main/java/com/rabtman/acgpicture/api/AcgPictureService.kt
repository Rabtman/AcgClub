package com.rabtman.acgpicture.api

import com.rabtman.acgpicture.base.constant.HtmlConstant
import com.rabtman.acgpicture.mvp.model.entity.AnimatePicturePage
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

/**
 * @author Rabtman
 */
interface AcgPictureService {

    /**
     * 获取动漫之家漫画列表信息
     */
    @GET(HtmlConstant.ANIME_PICTURE_URL + "pictures/view_posts/{pageIndex}?type=json&lang=en&posts_per_page=20")
    fun getAnimatePicture(@Path("pageIndex") pageIndex: Int): Flowable<AnimatePicturePage>

    @GET
    abstract fun getAcgPic(@Url url: String): Flowable<ResponseBody>

}