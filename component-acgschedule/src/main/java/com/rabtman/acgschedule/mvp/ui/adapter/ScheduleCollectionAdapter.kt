package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache
import com.rabtman.common.utils.DimenUtils
import com.rabtman.eximgloader.ImageLoader.loadImage
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @author Rabtman
 */
class ScheduleCollectionAdapter() : BaseQuickAdapter<ScheduleCache, BaseViewHolder>(R.layout.acgschedule_item_schedule_collection, null) {

    override fun convert(helper: BaseViewHolder, item: ScheduleCache) {

        helper.setText(R.id.tv_schedule_collection_name, item.name)
        helper.getView<ImageView>(R.id.img_schedule_collection).loadImage(item.imgUrl) {
            transformation(CropTransformation(0, 0), RoundedCornersTransformation(DimenUtils.dpToPx(context, 4f), 0))
        }
    }
}