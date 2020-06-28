package com.rabtman.acgschedule.mvp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleBanner
import com.rabtman.eximgloader.ImageLoader.loadImage
import com.zhouwei.mzbanner.holder.MZViewHolder

/**
 * @author Rabtman
 */
class ScheduleBannerViewHolder : MZViewHolder<ScheduleBanner> {
    private var bannerImg: ImageView? = null
    private var bannerTitle: TextView? = null
    override fun createView(context: Context): View {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.acgschedule_item_schedule_main_banner, null)
        bannerImg = view.findViewById(R.id.banner_scheudle_img)
        bannerTitle = view.findViewById(R.id.banner_schedule_title)
        return view
    }

    override fun onBind(context: Context, position: Int, scheudleBanner: ScheduleBanner) {
        bannerImg?.loadImage(context, scheudleBanner.imgUrl)
        bannerTitle?.text = scheudleBanner.name
    }
}