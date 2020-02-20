package com.rabtman.acgpicture.mvp.ui.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.rabtman.common.base.widget.PinchImageView
import com.rabtman.common.imageloader.ImageLoader
import com.rabtman.common.imageloader.glide.GlideImageConfig
import java.util.*

/**
 * @author Rabtman
 */

class AcgPictureDetailPageAdapter(private val mContext: Context,
                                  private val mImageLoader: ImageLoader,
                                  val imgUrls: List<String>) : PagerAdapter() {

    private val viewCache = LinkedList<PinchImageView>()
    private var pinchImageViewListener: PinchImageViewListener? = null

    fun setPinchImageViewListener(listener: PinchImageViewListener) {
        this.pinchImageViewListener = listener
    }

    override fun getCount(): Int {
        return imgUrls.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    fun getItems(): List<String> {
        return imgUrls
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val piv: PinchImageView
        if (viewCache.size > 0) {
            piv = viewCache.remove()
            piv.reset()
        } else {
            piv = PinchImageView(mContext)
            piv.setOnClickListener { v ->
                pinchImageViewListener?.onClick(v, position)
            }
        }
        mImageLoader.loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(imgUrls[position])
                        .imageView(piv)
                        .build())
        container.addView(piv)
        return piv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val piv = `object` as PinchImageView
        container.removeView(piv)
        viewCache.add(piv)
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        val piv = `object` as PinchImageView
        mImageLoader.loadImage(mContext,
                GlideImageConfig
                        .builder()
                        .url(imgUrls[position])
                        .imageView(piv)
                        .build())
    }

    interface PinchImageViewListener {
        fun onClick(v: View, pos: Int)
    }
}
