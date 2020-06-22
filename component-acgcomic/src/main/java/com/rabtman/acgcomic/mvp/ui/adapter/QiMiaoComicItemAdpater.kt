package com.rabtman.acgcomic.mvp.ui.adapter

import android.graphics.Bitmap
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation
import com.rabtman.common.utils.DimenUtils

/**
 * @author Rabtman
 */
class QiMiaoComicItemAdpater(private val imageLoader: ImageLoader) : BaseQuickAdapter<QiMiaoComicItem, BaseViewHolder>(R.layout.acgcomic_item_qimiao_comic, null) {

    override fun convert(helper: BaseViewHolder, item: QiMiaoComicItem) {
        helper.setText(R.id.tv_qimiao_comic_title, item?.title)
                .setText(R.id.tv_qimiao_comic_author, item?.author)
                .setText(R.id.tv_qimiao_comic_now, item?.now)
        imageLoader.loadImage(context,
                GlideImageConfig.builder()
                        .url(item?.imgUrl)
                        .imageView(helper.getView(R.id.img_qimiao_comic))
                        .transformation(
                                MultiTransformation<Bitmap>(CenterCrop(),
                                        RoundedCornersTransformation(DimenUtils.dpToPx(context, 4f), 0)))
                        .build()
        )
    }

}