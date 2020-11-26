package com.rabtman.acgpicture.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureItem
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */

class AcgPictureItemAdapter() : BaseQuickAdapter<AcgPictureItem, BaseViewHolder>(R.layout.acgpicture_item_acg, null), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: AcgPictureItem) {
        helper.setText(R.id.tv_acgpicture_title, item.title)
        helper.getViewOrNull<ImageView>(R.id.img_acgpicture_thumbnail)?.loadImage(context, item.thumbnail)
    }
}
