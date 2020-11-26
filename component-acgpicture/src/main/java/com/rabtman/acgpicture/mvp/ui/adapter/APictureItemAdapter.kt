package com.rabtman.acgpicture.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.model.entity.APictureItem
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */

class APictureItemAdapter() : BaseQuickAdapter<APictureItem, BaseViewHolder>(R.layout.acgpicture_item_apic, null), LoadMoreModule {


    override fun convert(helper: BaseViewHolder, item: APictureItem) {
        helper.setText(R.id.pic_title, item.title)
                .setText(R.id.pic_count, item.count)
        helper.getViewOrNull<ImageView>(R.id.pic_img)?.loadImage(context, item.thumbUrl)
    }
}
