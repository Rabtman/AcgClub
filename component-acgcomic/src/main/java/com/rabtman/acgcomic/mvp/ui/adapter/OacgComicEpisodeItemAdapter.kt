package com.rabtman.acgcomic.mvp.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode

/**
 * @author Rabtman
 */

class OacgComicEpisodeItemAdapter(data: List<OacgComicEpisode>) : BaseQuickAdapter<OacgComicEpisode, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic_episode, data) {

    //上次观看记录
    private var mLastRecordPos: Int = -1

    override fun convert(helper: BaseViewHolder, item: OacgComicEpisode) {
        helper.setText(R.id.tv_oacg_comic_episode_name, item.orderTitle)
        if (helper.adapterPosition == mLastRecordPos) {
            helper.setBackgroundRes(R.id.tv_oacg_comic_episode_name, R.drawable.acgcomic_btn_episode_record)
                    .setTextColor(R.id.tv_oacg_comic_episode_name,
                            ContextCompat.getColor(mContext, R.color.colorPrimary))
        } else {
            helper.setBackgroundRes(R.id.tv_oacg_comic_episode_name, R.drawable.acgcomic_btn_episode)
                    .setTextColor(R.id.tv_oacg_comic_episode_name, ContextCompat.getColor(mContext, R.color.grey400))
        }
    }

    /**
     * 设置最近一次观看记录
     *
     * @param pos 最近一次观看记录的位置
     */
    fun setRecordPos(pos: Int) {
        mLastRecordPos = pos
        notifyDataSetChanged()
    }
}
