package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.AcgComicItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */
class ComicItemAdpater(private val imageLoader: ImageLoader) : BaseQuickAdapter<AcgComicItem, BaseViewHolder>(R.layout.acgcomic_item_comic_info, null) {

    override fun convert(helper: BaseViewHolder?, item: AcgComicItem?) {
        if (helper != null) {
            helper.setText(R.id.tv_comic_name, item?.name)
                    .setText(R.id.tv_comic_update, item?.lastUpdateChapterName)
            imageLoader.loadImage(mContext,
                    GlideImageConfig
                            .builder()
                            .url(HtmlConstant.DMZJ_IMG_URL + item?.cover)
                            .imagerView(helper.getView(R.id.img_comic_main))
                            .build()
            )
        }
    }

}