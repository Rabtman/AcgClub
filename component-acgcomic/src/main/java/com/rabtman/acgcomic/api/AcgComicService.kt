package com.rabtman.acgcomic.api

import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.acgcomic.mvp.model.entity.OacgComicDetail
import com.rabtman.acgcomic.mvp.model.entity.OacgComicPage
import io.reactivex.Flowable
import retrofit2.http.*

/**
 * @author Rabtman
 */
interface AcgComicService {

    /**
     * 获取动漫之家漫画列表信息
     */
    @GET(HtmlConstant.DMZJ_URL + "classify/{selected}.json")
    fun getDmzjComicList(@Path("selected") selected: String): Flowable<List<DmzjComicItem>>

    /**
     * 获取Oacg漫画列表信息
     */
    @POST(HtmlConstant.OACG_URL + "index.php?m=Index&a=type_theme")
    @FormUrlEncoded
    fun getOacgComicList(@Field("theme_id") themeId: Int, @Field("pageidx") pageNo: Int): Flowable<OacgComicPage>

    /**
     * 获取Oacg指定漫画所有话
     */
    @POST(HtmlConstant.OACG_URL + "index.php?m=Index&a=content_catalog")
    @FormUrlEncoded
    fun getOacgComicDetail(@Field("comic_id") comicId: Int): Flowable<List<OacgComicDetail>>
}