package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail.ScheduleEpisode

/**
 * @author Rabtman
 */
class ScheduleDetailEpisodeItemAdapter(data: MutableList<ScheduleEpisode>?) : BaseQuickAdapter<ScheduleEpisode, BaseViewHolder>(R.layout.acgschedule_item_schedule_detail_episode, data) {
    //上次观看记录
    private var mLastRecordPos = -1
    private var itemCount = DEFAULT_ITEM_COUNT
    override fun convert(helper: BaseViewHolder, item: ScheduleEpisode) {
        helper.setText(R.id.tv_sd_episode_name, item.name)
        if (helper.adapterPosition == mLastRecordPos) {
            helper.setBackgroundResource(R.id.tv_sd_episode_name, R.drawable.acgschedule_btn_episode_record)
                    .setTextColor(R.id.tv_sd_episode_name,
                            ContextCompat.getColor(context, R.color.colorPrimary))
        } else {
            helper.setBackgroundResource(R.id.tv_sd_episode_name, R.drawable.acgschedule_btn_episode)
                    .setTextColor(R.id.tv_sd_episode_name,
                            ContextCompat.getColor(context, R.color.grey400))
        }
    }

    override fun getItemCount(): Int {
        if (data.size < DEFAULT_ITEM_COUNT) {
            itemCount = data.size
        }
        return itemCount
    }

    /**
     * 设置展示的项目数
     */
    fun setItemCount() {
        itemCount = data.size
        notifyDataSetChanged()
    }

    /**
     * 设置最近一次观看记录,并刷新ui
     *
     * @param pos 最近一次观看记录的位置
     */
    fun setRecordPos(pos: Int) {
        if (mLastRecordPos != -1) {
            val oldView = getViewByPosition(mLastRecordPos, R.id.tv_sd_episode_name) as TextView?
            if (oldView != null) {
                oldView.setBackgroundResource(R.drawable.acgschedule_btn_episode)
                oldView.setTextColor(ContextCompat.getColor(context, R.color.grey400))
            }
        }
        mLastRecordPos = pos
        val newView = getViewByPosition(mLastRecordPos, R.id.tv_sd_episode_name) as TextView?
        if (newView != null) {
            newView.setBackgroundResource(R.drawable.acgschedule_btn_episode_record)
            newView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        }
    }

    companion object {
        const val DEFAULT_ITEM_COUNT = 20
    }
}