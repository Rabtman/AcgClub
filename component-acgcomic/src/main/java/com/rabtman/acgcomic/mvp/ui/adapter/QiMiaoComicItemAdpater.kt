package com.rabtman.acgcomic.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.jsoup.QiMiaoComicItem
import com.rabtman.common.utils.DimenUtils
import com.rabtman.eximgloader.ImageLoader.loadImage
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @author Rabtman
 */
class QiMiaoComicItemAdpater() : BaseQuickAdapter<QiMiaoComicItem, BaseViewHolder>(R.layout.acgcomic_item_qimiao_comic, null), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: QiMiaoComicItem) {
        helper.setText(R.id.tv_qimiao_comic_title, item?.title)
                .setText(R.id.tv_qimiao_comic_author, item?.author)
                .setText(R.id.tv_qimiao_comic_now, item?.now)
        helper.getViewOrNull<ImageView>(R.id.img_qimiao_comic)?.loadImage(item?.imgUrl) {
            transformation(CropTransformation(0, 0), RoundedCornersTransformation(DimenUtils.dpToPx(context, 4f), 0))
        }
    }

}