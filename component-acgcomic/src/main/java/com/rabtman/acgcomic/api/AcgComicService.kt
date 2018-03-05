package com.rabtman.acgcomic.api

import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
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
    fun getOacgComicDetail(@Field("comic_id") comicId: Int): Flowable<List<OacgComicEpisode>>

    /**
     * 获取Oacg指定章节内容
     */
    @POST(HtmlConstant.OACG_URL + "index.php?m=Index&a=page_more")
    @FormUrlEncoded
    fun getOacgEpisodeDetail(@Field("comic_id") comicId: Int, @Field("chapter_index") chapterIndex: Int): Flowable<OacgComicEpisodePage>

    /**
     * Oacg漫画搜索
     */
    @POST(HtmlConstant.OACG_URL + "index.php?m=Index&a=searchlistdata")
    @FormUrlEncoded
    fun searchOacgComicInfos(@Field("tag") keyword: String): Flowable<OacgComicPage>
}