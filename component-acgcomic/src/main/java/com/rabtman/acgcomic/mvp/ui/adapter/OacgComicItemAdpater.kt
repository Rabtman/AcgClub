package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.OacgComicItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */
class OacgComicItemAdpater(private val imageLoader: ImageLoader) : BaseQuickAdapter<OacgComicItem, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic, null) {

    override fun convert(helper: BaseViewHolder?, item: OacgComicItem?) {
        if (helper != null) {
            helper.setText(R.id.tv_oacg_comic_title, item?.comicName)
                    .setText(R.id.tv_oacg_comic_painter, item?.painterUserNickname)
                    .setText(R.id.tv_oacg_comic_script, item?.scriptUserNickname)
                    .setText(R.id.tv_oacg_comic_spot, item?.comicTagName)
                    .setText(R.id.tv_oacg_comic_desc, item?.comicDesc)
            imageLoader.loadImage(mContext,
                    GlideImageConfig.builder()
                            .url(HtmlConstant.OACG_IMG_URL + item?.comicPicUrl)
                            .imageView(helper.getView(R.id.img_oacg_comic))
                            .build()
            )
        }
    }

}