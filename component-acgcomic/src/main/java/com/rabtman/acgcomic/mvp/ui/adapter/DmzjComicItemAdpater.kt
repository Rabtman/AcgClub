package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */
class DmzjComicItemAdpater(private val imageLoader: ImageLoader) : BaseQuickAdapter<DmzjComicItem, BaseViewHolder>(R.layout.acgcomic_item_dmzj_comic, null), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: DmzjComicItem) {
        if (helper != null) {
            helper.setText(R.id.tv_comic_name, item?.name)
                    .setText(R.id.tv_comic_update, item?.lastUpdateChapterName)
            imageLoader.loadImage(context,
                    GlideImageConfig
                            .builder()
                            .url(HtmlConstant.DMZJ_IMG_URL + item?.cover)
                            .imageView(helper.getView(R.id.img_comic))
                            .build()
            )
        }
    }

}