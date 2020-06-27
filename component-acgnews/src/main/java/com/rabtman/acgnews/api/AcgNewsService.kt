package com.rabtman.acgnews.api

import com.rabtman.acgnews.base.constant.HtmlConstant
import com.rabtman.acgnews.mvp.model.entity.SHPage
import com.rabtman.acgnews.mvp.model.entity.SHPostDetail
import com.rabtman.acgnews.mvp.model.entity.SHResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Rabtman
 */
interface AcgNewsService {
    @GET(HtmlConstant.ISH_URL + "ver/1f590097/article/list")
    fun getISHNews(@Query("page") pageIndex: Int,
                   @Query("pageSize") pageSize: Int, @Query("categoryID") categoryID: Int): Flowable<SHResponse<SHPage>>

    @GET(HtmlConstant.ISH_URL + "ver/3c590236/article/detail")
    fun getISHNewsDetail(@Query("id") postId: Int): Flowable<SHResponse<SHPostDetail>>
}