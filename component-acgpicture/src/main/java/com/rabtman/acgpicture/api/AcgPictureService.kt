package com.rabtman.acgpicture.api

import com.rabtman.acgpicture.base.constant.HtmlConstant
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureType
import com.rabtman.acgpicture.mvp.model.entity.AnimatePicturePage
import com.rabtman.common.http.BaseResponse
import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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
    fun getParseResponse(@Url url: String): Flowable<ResponseBody>

    @GET("picture/type")
    fun getAcgPictureType(): Flowable<List<AcgPictureType>>

    @GET("category/{type}/pictures")
    fun getAcgPictures(@Path("type") type: String, @Query("offset") pageNo: Int): Flowable<BaseResponse<List<AcgPictureItem>>>

}