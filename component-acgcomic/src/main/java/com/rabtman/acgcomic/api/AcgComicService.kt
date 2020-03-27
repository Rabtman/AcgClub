package com.rabtman.acgcomic.api

import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Rabtman
 */
interface AcgComicService {

    /**
     * 获取动漫之家漫画列表信息
     */
    @GET(HtmlConstant.DMZJ_URL + "classify/{selected}.json")
    fun getDmzjComicList(@Path("selected") selected: String): Flowable<List<DmzjComicItem>>

}