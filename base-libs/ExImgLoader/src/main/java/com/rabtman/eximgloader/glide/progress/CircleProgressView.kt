package com.rabtman.eximgloader.glide.progress

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ProgressBar
import androidx.annotation.IntDef
import com.rabtman.eximgloader.R
import com.rabtman.eximgloader.utils.Utils.dp2px
import com.rabtman.eximgloader.utils.Utils.sp2px

class CircleProgressView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ProgressBar(context, attrs, defStyleAttr) {
    private var mReachBarSize = dp2px(getContext(), 2f) // 未完成进度条大小
    private var mNormalBarSize = dp2px(getContext(), 2f) // 未完成进度条大小
    private var mReachBarColor = Color.parseColor("#108ee9") // 已完成进度颜色
    private var mNormalBarColor = Color.parseColor("#FFD3D6DA") // 未完成进度颜色
    private var mTextSize = sp2px(getContext(), 14f) // 进度值字体大小
    private var mTextColor = Color.parseColor("#108ee9") // 进度的值字体颜色
    private var mTextSkewX = 0f // 进度值字体倾斜角度 = 0f
    private var mTextSuffix = "%" // 进度值前缀
    private var mTextPrefix = "" // 进度值后缀
    private var mTextVisible = true // 是否显示进度值
    private var mReachCapRound = false// 画笔是否使用圆角边界，normalStyle下生效 = false
    private var mRadius = dp2px(getContext(), 20f) // 半径
    private var mStartArc = 0 // 起始角度 = 0
    private var mInnerBackgroundColor = 0 // 内部背景填充颜色 = 0
    private var mProgressStyle = ProgressStyle.NORMAL // 进度风格
    private var mInnerPadding = dp2px(getContext(), 1f) // 内部圆与外部圆间距
    private var mOuterColor = 0 // 外部圆环颜色 = 0
    private var needDrawInnerBackground = false // 是否需要绘制内部背景 = false
    private var rectF // 外部圆环绘制区域
            : RectF? = null
    private var rectInner // 内部圆环绘制区域
            : RectF? = null
    private var mOuterSize = dp2px(getContext(), 1f) // 外层圆环宽度
    private var mTextPaint // 绘制进度值字体画笔
            : Paint? = null
    private var mNormalPaint // 绘制未完成进度画笔
            : Paint? = null
    private var mReachPaint // 绘制已完成进度画笔
            : Paint? = null
    private var mInnerBackgroundPaint // 内部背景画笔
            : Paint? = null
    private var mOutPaint // 外部圆环画笔
            : Paint? = null
    private var mRealWidth = 0
    private var mRealHeight = 0

    @IntDef(ProgressStyle.NORMAL, ProgressStyle.FILL_IN, ProgressStyle.FILL_IN_ARC)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class ProgressStyle {
        companion object {
            const val NORMAL = 0
            const val FILL_IN = 1
            const val FILL_IN_ARC = 2
        }
    }


    private fun initPaint() {
        mTextPaint = Paint()
        mTextPaint!!.color = mTextColor
        mTextPaint!!.style = Paint.Style.FILL
        mTextPaint!!.textSize = mTextSize.toFloat()
        mTextPaint!!.textSkewX = mTextSkewX
        mTextPaint!!.isAntiAlias = true
        mNormalPaint = Paint()
        mNormalPaint!!.color = mNormalBarColor
        mNormalPaint!!.style = if (mProgressStyle == ProgressStyle.FILL_IN_ARC) Paint.Style.FILL else Paint.Style.STROKE
        mNormalPaint!!.isAntiAlias = true
        mNormalPaint!!.strokeWidth = mNormalBarSize.toFloat()
        mReachPaint = Paint()
        mReachPaint!!.color = mReachBarColor
        mReachPaint!!.style = if (mProgressStyle == ProgressStyle.FILL_IN_ARC) Paint.Style.FILL else Paint.Style.STROKE
        mReachPaint!!.isAntiAlias = true
        mReachPaint!!.strokeCap = if (mReachCapRound) Paint.Cap.ROUND else Paint.Cap.BUTT
        mReachPaint!!.strokeWidth = mReachBarSize.toFloat()
        if (needDrawInnerBackground) {
            mInnerBackgroundPaint = Paint()
            mInnerBackgroundPaint!!.style = Paint.Style.FILL
            mInnerBackgroundPaint!!.isAntiAlias = true
            mInnerBackgroundPaint!!.color = mInnerBackgroundColor
        }
        if (mProgressStyle == ProgressStyle.FILL_IN_ARC) {
            mOutPaint = Paint()
            mOutPaint!!.style = Paint.Style.STROKE
            mOutPaint!!.color = mOuterColor
            mOutPaint!!.strokeWidth = mOuterSize.toFloat()
            mOutPaint!!.isAntiAlias = true
        }
    }

    private fun obtainAttributes(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView)
        mProgressStyle = ta.getInt(R.styleable.CircleProgressView_cpv_progressStyle, ProgressStyle.NORMAL)
        // 获取三种风格通用的属性
        mNormalBarSize = ta.getDimension(R.styleable.CircleProgressView_cpv_progressNormalSize, mNormalBarSize.toFloat()).toInt()
        mNormalBarColor = ta.getColor(R.styleable.CircleProgressView_cpv_progressNormalColor, mNormalBarColor)
        mReachBarSize = ta.getDimension(R.styleable.CircleProgressView_cpv_progressReachSize, mReachBarSize.toFloat()).toInt()
        mReachBarColor = ta.getColor(R.styleable.CircleProgressView_cpv_progressReachColor, mReachBarColor)
        mTextSize = ta.getDimension(R.styleable.CircleProgressView_cpv_progressTextSize, mTextSize.toFloat()).toInt()
        mTextColor = ta.getColor(R.styleable.CircleProgressView_cpv_progressTextColor, mTextColor)
        mTextSkewX = ta.getDimension(R.styleable.CircleProgressView_cpv_progressTextSkewX, 0f)
        if (ta.hasValue(R.styleable.CircleProgressView_cpv_progressTextSuffix)) {
            mTextSuffix = ta.getString(R.styleable.CircleProgressView_cpv_progressTextSuffix)
        }
        if (ta.hasValue(R.styleable.CircleProgressView_cpv_progressTextPrefix)) {
            mTextPrefix = ta.getString(R.styleable.CircleProgressView_cpv_progressTextPrefix)
        }
        mTextVisible = ta.getBoolean(R.styleable.CircleProgressView_cpv_progressTextVisible, mTextVisible)
        mRadius = ta.getDimension(R.styleable.CircleProgressView_cpv_radius, mRadius.toFloat()).toInt()
        rectF = RectF((-mRadius).toFloat(), (-mRadius).toFloat(), mRadius.toFloat(), mRadius.toFloat())
        when (mProgressStyle) {
            ProgressStyle.FILL_IN -> {
                mReachBarSize = 0
                mNormalBarSize = 0
                mOuterSize = 0
            }
            ProgressStyle.FILL_IN_ARC -> {
                mStartArc = ta.getInt(R.styleable.CircleProgressView_cpv_progressStartArc, 0) + 270
                mInnerPadding = ta.getDimension(R.styleable.CircleProgressView_cpv_innerPadding, mInnerPadding.toFloat()).toInt()
                mOuterColor = ta.getColor(R.styleable.CircleProgressView_cpv_outerColor, mReachBarColor)
                mOuterSize = ta.getDimension(R.styleable.CircleProgressView_cpv_outerSize, mOuterSize.toFloat()).toInt()
                mReachBarSize = 0 // 将画笔大小重置为0
                mNormalBarSize = 0
                if (!ta.hasValue(R.styleable.CircleProgressView_cpv_progressNormalColor)) {
                    mNormalBarColor = Color.TRANSPARENT
                }
                val mInnerRadius = mRadius - mOuterSize / 2 - mInnerPadding
                rectInner = RectF((-mInnerRadius).toFloat(), (-mInnerRadius).toFloat(), mInnerRadius.toFloat(), mInnerRadius.toFloat())
            }
            ProgressStyle.NORMAL -> {
                mReachCapRound = ta.getBoolean(R.styleable.CircleProgressView_cpv_reachCapRound, true)
                mStartArc = ta.getInt(R.styleable.CircleProgressView_cpv_progressStartArc, 0) + 270
                if (ta.hasValue(R.styleable.CircleProgressView_cpv_innerBackgroundColor)) {
                    mInnerBackgroundColor = ta.getColor(R.styleable.CircleProgressView_cpv_innerBackgroundColor, Color.argb(0, 0, 0, 0))
                    needDrawInnerBackground = true
                }
            }
        }
        ta.recycle()
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxBarPaintWidth = Math.max(mReachBarSize, mNormalBarSize)
        val maxPaintWidth = Math.max(maxBarPaintWidth, mOuterSize)
        var height = 0
        var width = 0
        when (mProgressStyle) {
            ProgressStyle.FILL_IN -> {
                height = (paddingTop + paddingBottom // 边距
                        + Math.abs(mRadius * 2)) // 直径
                width = (paddingLeft + paddingRight // 边距
                        + Math.abs(mRadius * 2)) // 直径
            }
            ProgressStyle.FILL_IN_ARC -> {
                height = (paddingTop + paddingBottom // 边距
                        + Math.abs(mRadius * 2) // 直径
                        + maxPaintWidth) // 边框
                width = (paddingLeft + paddingRight // 边距
                        + Math.abs(mRadius * 2) // 直径
                        + maxPaintWidth) // 边框
            }
            ProgressStyle.NORMAL -> {
                height = (paddingTop + paddingBottom // 边距
                        + Math.abs(mRadius * 2) // 直径
                        + maxBarPaintWidth) // 边框
                width = (paddingLeft + paddingRight // 边距
                        + Math.abs(mRadius * 2) // 直径
                        + maxBarPaintWidth) // 边框
            }
        }
        mRealWidth = View.resolveSize(width, widthMeasureSpec)
        mRealHeight = View.resolveSize(height, heightMeasureSpec)
        setMeasuredDimension(mRealWidth, mRealHeight)
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        when (mProgressStyle) {
            ProgressStyle.NORMAL -> drawNormalCircle(canvas)
            ProgressStyle.FILL_IN -> drawFillInCircle(canvas)
            ProgressStyle.FILL_IN_ARC -> drawFillInArcCircle(canvas)
        }
    }

    /**
     * 绘制PROGRESS_STYLE_FILL_IN_ARC圆形
     */
    private fun drawFillInArcCircle(canvas: Canvas) {
        canvas.save()
        canvas.translate(mRealWidth / 2.toFloat(), mRealHeight / 2.toFloat())
        // 绘制外层圆环
        canvas.drawArc(rectF, 0f, 360f, false, mOutPaint)
        // 绘制内层进度实心圆弧
        // 内层圆弧半径
        val reachArc = progress * 1.0f / max * 360
        canvas.drawArc(rectInner, mStartArc.toFloat(), reachArc, true, mReachPaint)

        // 绘制未到达进度
        if (reachArc != 360f) {
            canvas.drawArc(rectInner, reachArc + mStartArc, 360 - reachArc, true, mNormalPaint)
        }
        canvas.restore()
    }

    /**
     * 绘制PROGRESS_STYLE_FILL_IN圆形
     */
    private fun drawFillInCircle(canvas: Canvas) {
        canvas.save()
        canvas.translate(mRealWidth / 2.toFloat(), mRealHeight / 2.toFloat())
        val progressY = progress * 1.0f / max * (mRadius * 2)
        val angle = (Math.acos((mRadius - progressY) / mRadius.toDouble()) * 180 / Math.PI).toFloat()
        val startAngle = 90 + angle
        val sweepAngle = 360 - angle * 2
        // 绘制未到达区域
        rectF = RectF((-mRadius).toFloat(), (-mRadius).toFloat(), mRadius.toFloat(), mRadius.toFloat())
        mNormalPaint!!.style = Paint.Style.FILL
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mNormalPaint)
        // 翻转180度绘制已到达区域
        canvas.rotate(180f)
        mReachPaint!!.style = Paint.Style.FILL
        canvas.drawArc(rectF, 270 - angle, angle * 2, false, mReachPaint)
        // 文字显示在最上层最后绘制
        canvas.rotate(180f)
        // 绘制文字
        if (mTextVisible) {
            val text = mTextPrefix + progress + mTextSuffix
            val textWidth = mTextPaint!!.measureText(text)
            val textHeight = mTextPaint!!.descent() + mTextPaint!!.ascent()
            canvas.drawText(text, -textWidth / 2, -textHeight / 2, mTextPaint)
        }
    }

    /**
     * 绘制PROGRESS_STYLE_NORMAL圆形
     */
    private fun drawNormalCircle(canvas: Canvas) {
        canvas.save()
        canvas.translate(mRealWidth / 2.toFloat(), mRealHeight / 2.toFloat())
        // 绘制内部圆形背景色
        if (needDrawInnerBackground) {
            canvas.drawCircle(0f, 0f, mRadius - Math.min(mReachBarSize, mNormalBarSize) / 2.toFloat(),
                    mInnerBackgroundPaint)
        }
        // 绘制文字
        if (mTextVisible) {
            val text = mTextPrefix + progress + mTextSuffix
            val textWidth = mTextPaint!!.measureText(text)
            val textHeight = mTextPaint!!.descent() + mTextPaint!!.ascent()
            canvas.drawText(text, -textWidth / 2, -textHeight / 2, mTextPaint)
        }
        // 计算进度值
        val reachArc = progress * 1.0f / max * 360
        // 绘制未到达进度
        if (reachArc != 360f) {
            canvas.drawArc(rectF, reachArc + mStartArc, 360 - reachArc, false, mNormalPaint)
        }
        // 绘制已到达进度
        canvas.drawArc(rectF, mStartArc.toFloat(), reachArc, false, mReachPaint)
        canvas.restore()
    }

    /**
     * 动画进度(0-当前进度)
     *
     * @param duration 动画时长
     */
    fun runProgressAnim(duration: Long) {
        setProgressInTime(0, duration)
    }

    /**
     * @param progress 进度值
     * @param duration 动画播放时间
     */
    fun setProgressInTime(progress: Int, duration: Long) {
        setProgressInTime(progress, getProgress(), duration)
    }

    /**
     * @param startProgress 起始进度
     * @param progress      进度值
     * @param duration      动画播放时间
     */
    fun setProgressInTime(startProgress: Int, progress: Int, duration: Long) {
        val valueAnimator = ValueAnimator.ofInt(startProgress, progress)
        valueAnimator.addUpdateListener { animator -> //获得当前动画的进度值，整型，1-100之间
            val currentValue = animator.animatedValue as Int
            setProgress(currentValue)
        }
        val interpolator = AccelerateDecelerateInterpolator()
        valueAnimator.interpolator = interpolator
        valueAnimator.duration = duration
        valueAnimator.start()
    }

    var reachBarSize: Int
        get() = mReachBarSize
        set(reachBarSize) {
            mReachBarSize = dp2px(context, reachBarSize.toFloat())
            invalidate()
        }

    var normalBarSize: Int
        get() = mNormalBarSize
        set(normalBarSize) {
            mNormalBarSize = dp2px(context, normalBarSize.toFloat())
            invalidate()
        }

    var reachBarColor: Int
        get() = mReachBarColor
        set(reachBarColor) {
            mReachBarColor = reachBarColor
            invalidate()
        }

    var normalBarColor: Int
        get() = mNormalBarColor
        set(normalBarColor) {
            mNormalBarColor = normalBarColor
            invalidate()
        }

    var textSize: Int
        get() = mTextSize
        set(textSize) {
            mTextSize = sp2px(context, textSize.toFloat())
            invalidate()
        }

    var textColor: Int
        get() = mTextColor
        set(textColor) {
            mTextColor = textColor
            invalidate()
        }

    var textSkewX: Float
        get() = mTextSkewX
        set(textSkewX) {
            mTextSkewX = textSkewX
            invalidate()
        }

    var textSuffix: String
        get() = mTextSuffix
        set(textSuffix) {
            mTextSuffix = textSuffix
            invalidate()
        }

    var textPrefix: String
        get() = mTextPrefix
        set(textPrefix) {
            mTextPrefix = textPrefix
            invalidate()
        }

    var isTextVisible: Boolean
        get() = mTextVisible
        set(textVisible) {
            mTextVisible = textVisible
            invalidate()
        }

    var isReachCapRound: Boolean
        get() = mReachCapRound
        set(reachCapRound) {
            mReachCapRound = reachCapRound
            invalidate()
        }

    var radius: Int
        get() = mRadius
        set(radius) {
            mRadius = dp2px(context, radius.toFloat())
            invalidate()
        }

    var startArc: Int
        get() = mStartArc
        set(startArc) {
            mStartArc = startArc
            invalidate()
        }

    var innerBackgroundColor: Int
        get() = mInnerBackgroundColor
        set(innerBackgroundColor) {
            mInnerBackgroundColor = innerBackgroundColor
            invalidate()
        }

    var progressStyle: Int
        get() = mProgressStyle
        set(progressStyle) {
            mProgressStyle = progressStyle
            invalidate()
        }

    var innerPadding: Int
        get() = mInnerPadding
        set(innerPadding) {
            mInnerPadding = dp2px(context, innerPadding.toFloat())
            val mInnerRadius = mRadius - mOuterSize / 2 - mInnerPadding
            rectInner = RectF((-mInnerRadius).toFloat(), (-mInnerRadius).toFloat(), mInnerRadius.toFloat(), mInnerRadius.toFloat())
            invalidate()
        }

    var outerColor: Int
        get() = mOuterColor
        set(outerColor) {
            mOuterColor = outerColor
            invalidate()
        }

    var outerSize: Int
        get() = mOuterSize
        set(outerSize) {
            mOuterSize = dp2px(context, outerSize.toFloat())
            invalidate()
        }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(STATE, super.onSaveInstanceState())
        // 保存当前样式
        bundle.putInt(PROGRESS_STYLE, progressStyle)
        bundle.putInt(RADIUS, radius)
        bundle.putBoolean(IS_REACH_CAP_ROUND, isReachCapRound)
        bundle.putInt(START_ARC, startArc)
        bundle.putInt(INNER_BG_COLOR, innerBackgroundColor)
        bundle.putInt(INNER_PADDING, innerPadding)
        bundle.putInt(OUTER_COLOR, outerColor)
        bundle.putInt(OUTER_SIZE, outerSize)
        // 保存text信息
        bundle.putInt(TEXT_COLOR, textColor)
        bundle.putInt(TEXT_SIZE, textSize)
        bundle.putFloat(TEXT_SKEW_X, textSkewX)
        bundle.putBoolean(TEXT_VISIBLE, isTextVisible)
        bundle.putString(TEXT_SUFFIX, textSuffix)
        bundle.putString(TEXT_PREFIX, textPrefix)
        // 保存已到达进度信息
        bundle.putInt(REACH_BAR_COLOR, reachBarColor)
        bundle.putInt(REACH_BAR_SIZE, reachBarSize)

        // 保存未到达进度信息
        bundle.putInt(NORMAL_BAR_COLOR, normalBarColor)
        bundle.putInt(NORMAL_BAR_SIZE, normalBarSize)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            val bundle = state
            mProgressStyle = bundle.getInt(PROGRESS_STYLE)
            mRadius = bundle.getInt(RADIUS)
            mReachCapRound = bundle.getBoolean(IS_REACH_CAP_ROUND)
            mStartArc = bundle.getInt(START_ARC)
            mInnerBackgroundColor = bundle.getInt(INNER_BG_COLOR)
            mInnerPadding = bundle.getInt(INNER_PADDING)
            mOuterColor = bundle.getInt(OUTER_COLOR)
            mOuterSize = bundle.getInt(OUTER_SIZE)
            mTextColor = bundle.getInt(TEXT_COLOR)
            mTextSize = bundle.getInt(TEXT_SIZE)
            mTextSkewX = bundle.getFloat(TEXT_SKEW_X)
            mTextVisible = bundle.getBoolean(TEXT_VISIBLE)
            mTextSuffix = bundle.getString(TEXT_SUFFIX)
            mTextPrefix = bundle.getString(TEXT_PREFIX)
            mReachBarColor = bundle.getInt(REACH_BAR_COLOR)
            mReachBarSize = bundle.getInt(REACH_BAR_SIZE)
            mNormalBarColor = bundle.getInt(NORMAL_BAR_COLOR)
            mNormalBarSize = bundle.getInt(NORMAL_BAR_SIZE)
            initPaint()
            super.onRestoreInstanceState(bundle.getParcelable(STATE))
            return
        }
        super.onRestoreInstanceState(state)
    }

    override fun invalidate() {
        initPaint()
        super.invalidate()
    }

    companion object {
        private const val STATE = "state"
        private const val PROGRESS_STYLE = "progressStyle"
        private const val TEXT_COLOR = "textColor"
        private const val TEXT_SIZE = "textSize"
        private const val TEXT_SKEW_X = "textSkewX"
        private const val TEXT_VISIBLE = "textVisible"
        private const val TEXT_SUFFIX = "textSuffix"
        private const val TEXT_PREFIX = "textPrefix"
        private const val REACH_BAR_COLOR = "reachBarColor"
        private const val REACH_BAR_SIZE = "reachBarSize"
        private const val NORMAL_BAR_COLOR = "normalBarColor"
        private const val NORMAL_BAR_SIZE = "normalBarSize"
        private const val IS_REACH_CAP_ROUND = "isReachCapRound"
        private const val RADIUS = "radius"
        private const val START_ARC = "startArc"
        private const val INNER_BG_COLOR = "innerBgColor"
        private const val INNER_PADDING = "innerPadding"
        private const val OUTER_COLOR = "outerColor"
        private const val OUTER_SIZE = "outerSize"
    }

    init {
        obtainAttributes(attrs)
        initPaint()
    }
}