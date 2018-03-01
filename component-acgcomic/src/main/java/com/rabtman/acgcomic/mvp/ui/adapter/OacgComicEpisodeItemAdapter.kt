package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.OacgComicDetail

/**
 * @author Rabtman
 */

class OacgComicEpisodeItemAdapter(data: List<OacgComicDetail>) : BaseQuickAdapter<OacgComicDetail, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic_episode, data) {

    override fun convert(helper: BaseViewHolder, item: OacgComicDetail) {
        helper.setText(R.id.tv_oacg_comic_episode_name, item.orderTitle)
    }
}
