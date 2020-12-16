package com.rabtman.acgschedule.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleOtherPage.ScheduleOtherItem
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */
class ScheduleOtherAdapter() : BaseQuickAdapter<ScheduleOtherItem, BaseViewHolder>(R.layout.acgschedule_item_schedule_other, null), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: ScheduleOtherItem) {
        helper.setText(R.id.tv_schedule_other_title, item.title)
        helper.getView<ImageView>(R.id.img_schedule_other).loadImage(item.imgUrl)
    }
}