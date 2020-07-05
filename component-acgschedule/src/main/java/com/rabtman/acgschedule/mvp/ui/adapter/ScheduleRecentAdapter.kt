package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleInfo.ScheduleRecent
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */
class ScheduleRecentAdapter(data: MutableList<ScheduleRecent>?) : BaseQuickAdapter<ScheduleRecent, BaseViewHolder>(R.layout.acgschedule_item_schedule_recent, data) {

    override fun convert(helper: BaseViewHolder, item: ScheduleRecent) {
        helper.setText(R.id.tv_schedule_recent, item.name)
                .setText(R.id.tv_schedule_recent_desc, item.desc)

        helper.getView<ImageView>(R.id.img_schedule_recent).loadImage(context, item.getImgUrl())
    }

}