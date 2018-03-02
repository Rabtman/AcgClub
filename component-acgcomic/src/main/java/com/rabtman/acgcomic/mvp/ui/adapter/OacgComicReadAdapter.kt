package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */

class OacgComicReadAdapter(private val imgageLoader: ImageLoader) : BaseQuickAdapter<OacgComicEpisodePage.PageContent, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic_read, null) {

    override fun convert(helper: BaseViewHolder, item: OacgComicEpisodePage.PageContent) {
        imgageLoader.loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(HtmlConstant.OACG_IMG_URL + item.pagerPic)
                        .override(item.pagerPicWidth.toInt(), item.pagerPicHeight.toInt())
                        .imagerView(helper.getView(R.id.img_oacg_comic_read))
                        .build()
        )
    }
}