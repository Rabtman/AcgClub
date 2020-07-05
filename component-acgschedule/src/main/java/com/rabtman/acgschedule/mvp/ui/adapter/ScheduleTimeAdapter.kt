package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.base.constant.SystemConstant
import com.rabtman.acgschedule.mvp.model.entity.ScheduleTimeItem

/**
 * @author Rabtman
 */
class ScheduleTimeAdapter(data: MutableList<ScheduleTimeItem>?)
    : BaseSectionQuickAdapter<ScheduleTimeItem, BaseViewHolder>(R.layout.acgschedule_item_schedule_time_header, R.layout.acgschedule_item_schedule_time_item, data) {
    override fun convert(helper: BaseViewHolder, item: ScheduleTimeItem) {
        helper.setText(R.id.schedule_name, item.data!!.name)
                .setText(R.id.schedule_episode, item.data!!.episode)
    }

    override fun convertHeader(helper: BaseViewHolder,
                               item: ScheduleTimeItem) {
        helper.getView<TextView>(R.id.schedule_time_title).apply {
            //标题
            text = item.header
            //标题图标
            setCompoundDrawablesWithIntrinsicBounds(
                    ContextCompat.getDrawable(context,
                            SystemConstant.SCHEDULE_WEEK_LIST_TITLE_DRAWABLE[item.headerIndex]), null, null, null)
        }

    }
}