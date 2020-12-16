package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleNew.ScheduleNewItem
import com.rabtman.common.utils.DimenUtils
import com.rabtman.eximgloader.ImageLoader.loadImage
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @author Rabtman
 */
class ScheduleNewAdapter() : BaseQuickAdapter<ScheduleNewItem, BaseViewHolder>(R.layout.acgschedule_item_schedule_new, null) {

    override fun convert(helper: BaseViewHolder, item: ScheduleNewItem) {
        helper.setText(R.id.schedule_new_title, item.title)
                .setText(R.id.schedule_new_spot, item.spot)
                .setText(R.id.schedule_new_type, item.type)
                .setText(R.id.schedule_new_desc, item.desc)

        helper.getView<ImageView>(R.id.schedule_new_img)
                .loadImage(item.imgUrl) {
                    transformation(CropTransformation(0, 0), RoundedCornersTransformation(DimenUtils.dpToPx(context, 4f), 0))
                }
    }
}