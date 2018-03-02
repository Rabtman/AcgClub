package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisode

/**
 * @author Rabtman
 */

class OacgComicEpisodeItemAdapter(data: List<OacgComicEpisode>) : BaseQuickAdapter<OacgComicEpisode, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic_episode, data) {

    override fun convert(helper: BaseViewHolder, item: OacgComicEpisode) {
        helper.setText(R.id.tv_oacg_comic_episode_name, item.orderTitle)
    }
}
