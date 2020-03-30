package com.rabtman.acgcomic.mvp.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicChapter

/**
 * @author Rabtman
 */

class QiMiaoComicEpisodeItemAdapter : BaseQuickAdapter<QiMiaoComicChapter, BaseViewHolder>(R.layout.acgcomic_item_qimiao_comic_episode, null) {

    companion object {
        const val DEFAULT_ITEM_COUNT = 20
    }

    //上次观看记录
    private var mLastChapterId: String = ""
    private var episodeItemCount = DEFAULT_ITEM_COUNT

    override fun convert(helper: BaseViewHolder, item: QiMiaoComicChapter) {
        helper.setText(R.id.tv_qimiao_comic_episode_name, item.name)
        if (item.chapterId == mLastChapterId) {
            helper.setBackgroundRes(R.id.tv_qimiao_comic_episode_name, R.drawable.acgcomic_btn_episode_record)
                    .setTextColor(R.id.tv_qimiao_comic_episode_name,
                            ContextCompat.getColor(mContext, R.color.colorPrimary))
        } else {
            helper.setBackgroundRes(R.id.tv_qimiao_comic_episode_name, R.drawable.acgcomic_btn_episode)
                    .setTextColor(R.id.tv_qimiao_comic_episode_name, ContextCompat.getColor(mContext, R.color.grey400))
        }
    }

    override fun getItemCount(): Int {
        if (mData != null && mData.size < DEFAULT_ITEM_COUNT) {
            episodeItemCount = mData.size
        }
        return episodeItemCount
    }

    /**
     * 设置展示的项目数
     */
    fun setItemCount() {
        this.episodeItemCount = mData.size
        notifyDataSetChanged()
    }

    /**
     * 设置最近一次观看记录
     *
     * @param pos 最近一次观看记录的位置
     */
    fun setRecordPos(pos: String) {
        mLastChapterId = pos
        //notifyItemChanged(pos)
        notifyDataSetChanged()
    }
}
