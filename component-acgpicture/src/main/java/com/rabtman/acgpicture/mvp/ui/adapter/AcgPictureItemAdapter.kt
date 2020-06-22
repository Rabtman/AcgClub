package com.rabtman.acgpicture.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */

class AcgPictureItemAdapter(private val mImageLoader: ImageLoader) : BaseQuickAdapter<AcgPictureItem, BaseViewHolder>(R.layout.acgpicture_item_acg, null) {

    override fun convert(helper: BaseViewHolder, item: AcgPictureItem) {
        helper.setText(R.id.tv_acgpicture_title, item.title)
        mImageLoader.loadImage(context,
                GlideImageConfig
                        .builder()
                        .url(item.thumbnail)
                        .imageView(helper.getView(R.id.img_acgpicture_thumbnail) as ImageView)
                        .build()
        )
    }
}
