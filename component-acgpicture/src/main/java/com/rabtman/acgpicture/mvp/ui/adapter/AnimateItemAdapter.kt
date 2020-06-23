package com.rabtman.acgpicture.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.model.entity.AnimatePictureItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */

class AnimateItemAdapter(private val mImageLoader: ImageLoader) : BaseQuickAdapter<AnimatePictureItem, BaseViewHolder>(R.layout.acgpicture_item_animate_item, null), LoadMoreModule {


    override fun convert(helper: BaseViewHolder, item: AnimatePictureItem) {
        mImageLoader.loadImage(context,
                GlideImageConfig
                        .builder()
                        .url(item.smallPreview)
                        .imageView(helper.getView(R.id.img_animate) as ImageView)
                        .build()
        )
    }
}
