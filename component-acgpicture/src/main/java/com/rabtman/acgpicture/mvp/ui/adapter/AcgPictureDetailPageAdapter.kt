package com.rabtman.acgpicture.mvp.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.rabtman.common.base.widget.PinchImageView
import com.rabtman.eximgloader.ImageLoader.loadImage
import java.util.*

/**
 * @author Rabtman
 */

class AcgPictureDetailPageAdapter(private val mContext: Context,
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
        piv.loadImage(mContext, imgUrls[position])
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
        piv.loadImage(mContext, imgUrls[position])
    }

    interface PinchImageViewListener {
        fun onClick(v: View, pos: Int)
    }
}
