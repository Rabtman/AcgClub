package com.rabtman.acgcomic.mvp.ui.adapter

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.rabtman.acgcomic.R
import com.rabtman.common.imageloader.glide.GlideApp
import com.rabtman.common.utils.DimenUtils
import kotlin.math.roundToInt

/**
 * @author Rabtman
 */

class QiMiaoComicReadAdapter() : BaseQuickAdapter<String, BaseViewHolder>(R.layout.acgcomic_item_qimiao_comic_read, null) {

    private var screenWidth: Int = 0

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        screenWidth = DimenUtils.getScreenWidth(mContext)
        super.onBindViewHolder(holder, position)
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.tv_qimiao_comic_pos, (helper.adapterPosition + 1).toString())
        GlideApp.with(mContext)
                .asBitmap()
                .load(item)
                .fallback(R.drawable.ic_launcher_round)
                .error(R.drawable.ic_launcher_round)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        helper.getView<TextView>(R.id.tv_qimiao_comic_pos).visibility = View.GONE
                        val img: ImageView = helper.getView(R.id.img_qimiao_comic_read)
                        img.setImageBitmap(resource)
                        val scale = screenWidth.toDouble() / resource.width.toDouble()
                        val layoutParams = img.layoutParams
                        layoutParams.width = (resource.width * scale).roundToInt()
                        layoutParams.height = (resource.height * scale).roundToInt()
                        img.layoutParams = layoutParams
                    }
                })

    }
}