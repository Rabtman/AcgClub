package com.rabtman.acgcomic.mvp.model.dto

import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache

/**
 * @author Rabtman
 */
data class ComicReadRecord(val pageData: OacgComicEpisodePage,
                           val cache: ComicCache)