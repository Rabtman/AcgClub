package com.rabtman.eximgloader.glide.progress

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition

class GlideImageViewTarget(view: ImageView?, var url: String?) : DrawableImageViewTarget(view) {

    override fun onLoadFailed(errorDrawable: Drawable?) {
        url?.apply {
            val onProgressListener = ProgressManager.getProgressListener(this)
            if (onProgressListener != null) {
                onProgressListener.onProgress(false, 100, 0, 0)
                ProgressManager.removeListener(this)
            }
        }
        super.onLoadFailed(errorDrawable)
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        url?.apply {
            val onProgressListener = ProgressManager.getProgressListener(this)
            if (onProgressListener != null) {
                onProgressListener.onProgress(true, 100, 0, 0)
                ProgressManager.removeListener(this)
            }
        }
        super.onResourceReady(resource, transition)
    }
}