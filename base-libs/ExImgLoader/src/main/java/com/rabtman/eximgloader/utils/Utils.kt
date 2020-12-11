package com.rabtman.eximgloader.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.ColorRes

object Utils {
    private var windowManager: WindowManager? = null
    private fun getWindowManager(context: Context): WindowManager? {
        if (windowManager == null) {
            windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        }
        return windowManager
    }

    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }

    fun getFontDensity(context: Context): Float {
        return context.resources.displayMetrics.scaledDensity
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        getWindowManager(context)!!.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    @JvmStatic
    fun dp2px(context: Context, dp: Float): Int {
        return (getDensity(context) * dp + 0.5f).toInt()
    }

    fun px2dp(context: Context, px: Float): Int {
        return (px / getDensity(context) + 0.5f).toInt()
    }

    @JvmStatic
    fun sp2px(context: Context, sp: Float): Int {
        return (getFontDensity(context) * sp + 0.5f).toInt()
    }

    fun px2sp(context: Context, px: Float): Int {
        return (px / getFontDensity(context) + 0.5f).toInt()
    }

    fun getWindowWidth(context: Context): Int {
        return getDisplayMetrics(context).widthPixels
    }

    fun getWindowHeight(context: Context): Int {
        return getDisplayMetrics(context).heightPixels
    }

    fun getPathFormat(path: String): String {
        if (!TextUtils.isEmpty(path)) {
            val lastPeriodIndex = path.lastIndexOf('.')
            if (lastPeriodIndex > 0 && lastPeriodIndex + 1 < path.length) {
                val format = path.substring(lastPeriodIndex + 1)
                if (!TextUtils.isEmpty(format)) {
                    return format.toLowerCase()
                }
            }
        }
        return ""
    }

    fun isGif(url: String): Boolean {
        return "gif" == getPathFormat(url)
    }

    fun getTextBitmap(context: Context, width: Int, height: Int, radius: Int, text: String?, textSize: Int, @ColorRes bgColor: Int): Bitmap {
        var radius = radius
        radius = dp2px(context, radius.toFloat())
        val bitmap = Bitmap.createBitmap(dp2px(context, width.toFloat()), dp2px(context, height.toFloat()), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val rect = RectF(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat())
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = context.resources.getColor(bgColor)
        canvas.drawRoundRect(RectF(0F, 0F, rect.width(), rect.height()), radius.toFloat(), radius.toFloat(), paint)
        paint.color = Color.WHITE
        paint.textSize = dp2px(context, textSize.toFloat()).toFloat()
        paint.textAlign = Paint.Align.CENTER
        val fontMetrics = paint.fontMetricsInt
        val baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2
        canvas.drawText(text, rect.centerX(), baseline, paint)
        return bitmap
    }

    fun getTextDrawable(context: Context, width: Int, height: Int, radius: Int, text: String?, textSize: Int, @ColorRes bgColor: Int): Drawable {
        return BitmapDrawable(getTextBitmap(context, width, height, radius, text, textSize, bgColor))
    }

    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun getSize(collection: Collection<*>?): Int {
        return collection?.size ?: 0
    }
}