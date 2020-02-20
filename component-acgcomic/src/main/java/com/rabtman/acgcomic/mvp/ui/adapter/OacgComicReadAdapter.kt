package com.rabtman.acgcomic.mvp.ui.adapter

import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.acgcomic.base.constant.HtmlConstant
import com.rabtman.acgcomic.mvp.model.entity.OacgComicEpisodePage
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig
import com.rabtman.common.utils.DimenUtils

/**
 * @author Rabtman
 */

class OacgComicReadAdapter(private val imgageLoader: ImageLoader) : BaseQuickAdapter<OacgComicEpisodePage.PageContent, BaseViewHolder>(R.layout.acgcomic_item_oacg_comic_read, null) {

    override fun convert(helper: BaseViewHolder, item: OacgComicEpisodePage.PageContent) {
        helper.setText(R.id.tv_oacg_comic_pos, (helper.adapterPosition + 1).toString())
        //根据屏幕尺寸来缩放加载图片的大小
        val screenWidth = DimenUtils.getScreenWidth(mContext)
        val layoutRead = helper.getView(R.id.layout_oacg_comic_read) as FrameLayout
        layoutRead.layoutParams.width = screenWidth
        layoutRead.layoutParams.height = (screenWidth / (item.pagerPicWidth.toDouble() / item.pagerPicHeight.toDouble())).toInt()

        imgageLoader.loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(HtmlConstant.OACG_IMG_URL + item.pagerPic)
                        .imageView(helper.getView(R.id.img_oacg_comic_read))
                        .build()
        )
    }
}