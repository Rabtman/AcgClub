package com.rabtman.acgcomic.api

import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.acgcomic.mvp.model.entity.QiMiaoChapterContent
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author Rabtman
 */
interface AcgComicService {

    /**
     * 获取动漫之家漫画列表信息
     */
    @GET(HtmlConstant.DMZJ_URL + "classify/{selected}.json")
    fun getDmzjComicList(@Path("selected") selected: String): Flowable<List<DmzjComicItem>>

    @GET(HtmlConstant.QIMIAO_URL + "/Action/Play/AjaxLoadImgUrl")
    fun getQiMiaoChapterContent(@Query("did") comicId: String, @Query("sid") chapterId: String, @Query("tmp") random: Double): Flowable<QiMiaoChapterContent>
}