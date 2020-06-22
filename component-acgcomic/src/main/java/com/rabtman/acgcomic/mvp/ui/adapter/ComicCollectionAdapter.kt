package com.rabtman.acgcomic.mvp.ui.adapter

import android.graphics.Bitmap
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.imageloader.glide.transformations.RoundedCornersTransformation
import com.rabtman.common.utils.DimenUtils

/**
 * @author Rabtman
 */

class ComicCollectionAdapter(private val mImageLoader: ImageLoader) : BaseQuickAdapter<ComicCache, BaseViewHolder>(R.layout.acgcomic_item_comic_collection, null) {


    override fun convert(helper: BaseViewHolder, item: ComicCache) {
        helper.setText(R.id.tv_comic_collection_name, item.comicName)
        mImageLoader.loadImage(context,
                GlideImageConfig
                        .builder()
                        .url(item.comicImgUrl)
                        .transformation(
                                MultiTransformation<Bitmap>(CenterCrop(),
                                        RoundedCornersTransformation(DimenUtils.dpToPx(context, 4f), 0)))
                        .imageView(helper.getView(R.id.img_comic_collection))
                        .build()
        )
    }
}
