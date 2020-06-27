package com.rabtman.eximgloader

import android.widget.ImageView

/**
 * @author Rabtman
 */
interface ImageLoadListener {
    fun loadFail(target: ImageView?, e: Exception?)
    fun loadReady(target: ImageView?)
}