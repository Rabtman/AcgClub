package com.rabtman.acgmusic.mvp.ui.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.RESTART
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.ImageView

class RotateImageView @JvmOverloads constructor(internal var context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr) {

    val ratoteAnimator: ObjectAnimator

    init {
        ratoteAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        ratoteAnimator.setInterpolator(LinearInterpolator())
        ratoteAnimator.setDuration(20000)
        ratoteAnimator.repeatMode = RESTART
        ratoteAnimator.repeatCount = INFINITE
    }

    fun start() {
        if (ratoteAnimator.isStarted) {
            ratoteAnimator.resume()
        } else {
            ratoteAnimator.start()
        }
    }

    fun pause() {
        ratoteAnimator.pause()
    }

    fun reset() {
        if (ratoteAnimator.isStarted) {
            ratoteAnimator.end()
        }
    }

}
