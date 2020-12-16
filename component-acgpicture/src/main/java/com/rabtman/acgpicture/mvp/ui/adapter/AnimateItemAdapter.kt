package com.rabtman.acgpicture.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.mvp.model.entity.AnimatePictureItem
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */

class AnimateItemAdapter() : BaseQuickAdapter<AnimatePictureItem, BaseViewHolder>(R.layout.acgpicture_item_animate_item, null), LoadMoreModule {


    override fun convert(helper: BaseViewHolder, item: AnimatePictureItem) {
        helper.getViewOrNull<ImageView>(R.id.img_animate)?.loadImage(item.smallPreview)
    }
}
