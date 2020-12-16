package com.rabtman.acgcomic.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.mvp.model.entity.db.ComicCache
import com.rabtman.common.utils.DimenUtils
import com.rabtman.eximgloader.ImageLoader.loadImage
import jp.wasabeef.glide.transformations.CropTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @author Rabtman
 */

class ComicCollectionAdapter() : BaseQuickAdapter<ComicCache, BaseViewHolder>(R.layout.acgcomic_item_comic_collection, null) {


    override fun convert(helper: BaseViewHolder, item: ComicCache) {
        helper.setText(R.id.tv_comic_collection_name, item.comicName)
        helper.getViewOrNull<ImageView>(R.id.img_comic_collection)?.loadImage(item.comicImgUrl) {
            transformation(CropTransformation(0, 0), RoundedCornersTransformation(DimenUtils.dpToPx(context, 4f), 0))
        }

    }
}
