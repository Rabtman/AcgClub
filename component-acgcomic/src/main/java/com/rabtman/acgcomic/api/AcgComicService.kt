package com.rabtman.acgcomic.api

import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.AcgComicItem
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Rabtman
 */
interface AcgComicService {

    @GET(HtmlConstant.DMZJ_URL + "classify/{selected}.json")
    fun getComicList(@Path("selected") selected: String): Flowable<List<AcgComicItem>>

}