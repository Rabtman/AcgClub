package com.rabtman.acgnews.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgnews.R
import com.rabtman.acgnews.mvp.model.entity.SHPostItem
import com.rabtman.common.utils.TimeUtils
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */
class ISHNewsItemAdapter : BaseQuickAdapter<SHPostItem, BaseViewHolder>(R.layout.acgnews_item_news_item, null), LoadMoreModule {
    override fun convert(helper: BaseViewHolder, item: SHPostItem) {
        helper.setText(R.id.news_title, item.title)
                .setText(R.id.news_datetime, TimeUtils.millis2String(item.time))
        helper.getView<ImageView>(R.id.news_img).loadImage(item.thumb)
    }
}