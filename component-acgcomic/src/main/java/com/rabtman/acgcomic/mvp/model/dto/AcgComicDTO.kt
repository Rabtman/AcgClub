package com.rabtman.acgcomic.mvp.model.dto

import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicEpisodeDetail

/**
 * @author Rabtman
 */
data class ComicReadRecord(val pageData: QiMiaoComicEpisodeDetail,
                           val cache: ComicCache)