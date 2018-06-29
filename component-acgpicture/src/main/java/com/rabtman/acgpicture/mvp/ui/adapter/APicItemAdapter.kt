package com.rabtman.acgpicture.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.model.entity.APictureItem
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */

class APicItemAdapter(private val mImageLoader: ImageLoader) : BaseQuickAdapter<APictureItem, BaseViewHolder>(R.layout.acgpicture_item_apic, null) {


    override fun convert(helper: BaseViewHolder, item: APictureItem) {
        helper.setText(R.id.pic_title, item.title)
                .setText(R.id.pic_count, item.count)
        mImageLoader.loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(item.thumbUrl)
                        .imagerView(helper.getView(R.id.pic_img) as ImageView)
                        .build()
        )
    }
}
