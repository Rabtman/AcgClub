package com.rabtman.acgcomic.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig

/**
 * @author Rabtman
 */

class QiMiaoComicReadAdapter(private val imgageLoader: ImageLoader) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic_read, null) {

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_oacg_comic_pos, (helper.adapterPosition + 1).toString())
        //根据屏幕尺寸来缩放加载图片的大小
        /*val screenWidth = DimenUtils.getScreenWidth(mContext)
        val layoutRead = helper.getView(R.id.layout_oacg_comic_read) as FrameLayout
        layoutRead.layoutParams.width = screenWidth
        layoutRead.layoutParams.height = (screenWidth / (item.pagerPicWidth.toDouble() / item.pagerPicHeight.toDouble())).toInt()*/

        imgageLoader.loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(item)
                        .imageView(helper.getView(R.id.img_oacg_comic_read))
                        .build()
        )
    }
}