package com.rabtman.acgschedule.mvp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rabtman.acgschedule.R
import com.rabtman.acgschedule.mvp.model.jsoup.DilidiliInfo.ScheduleBanner
import com.rabtman.eximgloader.ImageLoader.loadImage
import com.youth.banner.adapter.BannerAdapter


/**
 * @author Rabtman
 */
class ScheduleBannerAdapter(data: MutableList<ScheduleBanner>) : BannerAdapter<ScheduleBanner, ScheduleBannerAdapter.BannerViewHolder>(data) {
    private var ctx: Context? = null

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder? {
        ctx = parent.context
        return BannerViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.acgschedule_item_schedule_main_banner, parent, false))
    }

    override fun onBindView(holder: BannerViewHolder, scheudleBanner: ScheduleBanner, position: Int, size: Int) {
        ctx?.let { holder.bannerImg.loadImage(it, scheudleBanner.imgUrl) }
        holder.bannerTitle.text = scheudleBanner.name
    }

    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var bannerImg: ImageView = itemView.findViewById(R.id.banner_scheudle_img)
        var bannerTitle: TextView = itemView.findViewById(R.id.banner_schedule_title)
    }
}