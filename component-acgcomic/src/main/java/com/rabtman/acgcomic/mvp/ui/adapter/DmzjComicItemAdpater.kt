package com.rabtman.acgcomic.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.DmzjComicItem
import com.rabtman.eximgloader.ImageLoader.loadImage

/**
 * @author Rabtman
 */
class DmzjComicItemAdpater() : BaseQuickAdapter<DmzjComicItem, BaseViewHolder>(R.layout.acgcomic_item_dmzj_comic, null), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: DmzjComicItem) {
        if (helper != null) {
            helper.setText(R.id.tv_comic_name, item?.name)
                    .setText(R.id.tv_comic_update, item?.lastUpdateChapterName)
            helper.getViewOrNull<ImageView>(R.id.img_comic)?.loadImage(HtmlConstant.DMZJ_IMG_URL + item?.cover)
        }
    }

}