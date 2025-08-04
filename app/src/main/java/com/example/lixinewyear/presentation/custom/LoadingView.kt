package com.example.lixinewyear.presentation.custom

import android.animation.Animator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.lixinewyear.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LoadingView: FrameLayout {

    companion object {
        private const val PROPERTY_START_ANGLE = "PROPERTY_START_ANGLE"
    }

    private var mValueAnimator = ValueAnimator()
    private var mSizeProgressBar =
        context.resources.getDimension(R.dimen.size_progress_loading).roundToInt()

    private lateinit var mProgressView:ImageView
    private lateinit var mLogoCenter:ImageView
    private var mWithProgress: Boolean = false

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        mProgressView = ImageView(context)
        mProgressView.setImageDrawable(
            ContextCompat.getDrawable(context, R.drawable.eos_icons_loading)
        )
        addView(mProgressView,mSizeProgressBar,mSizeProgressBar)
        (mProgressView.layoutParams as? LayoutParams)?.gravity = Gravity.CENTER

        mLogoCenter = ImageView(context)
        mLogoCenter.setImageDrawable(
            ContextCompat.getDrawable(context,R.drawable.ic_app)
        )
//        mLogoCenter.imageTintList = ColorStateList.valueOf(
//            context.getColor(R.color.colorRed)
//        )
        val sizeIconCenter = (mSizeProgressBar * 0.25f).roundToInt()
        addView(mLogoCenter,sizeIconCenter,sizeIconCenter)
        (mLogoCenter.layoutParams as? LayoutParams)?.gravity = Gravity.CENTER

        attrs?.let { attributeSet ->
            context.obtainStyledAttributes(attributeSet, R.styleable.LoadingView).apply {
                mWithProgress = this.getBoolean(R.styleable.LoadingView_with_progress, false)
                if (mWithProgress) {
                    mLogoCenter.imageTintList = ColorStateList.valueOf(
                        context.getColor(R.color.colorWhite)
                    )
                    mProgressView.setImageDrawable(
                        ContextCompat.getDrawable(context,R.drawable.eos_icons_loading_white)
                    )
                }
                recycle()
            }

        }
        MainScope().launch {
            mValueAnimator.removeAllUpdateListeners()
            mValueAnimator.interpolator = LinearInterpolator()
            val propertyStartAngle = PropertyValuesHolder.ofFloat(PROPERTY_START_ANGLE, 0f, 360f)
            mValueAnimator.setValues(
                propertyStartAngle
            )
            mValueAnimator.duration = 500
            mValueAnimator.repeatCount = ValueAnimator.INFINITE
            mValueAnimator.addUpdateListener { animation ->
                mProgressView.rotation = animation.getAnimatedValue(PROPERTY_START_ANGLE) as Float
            }
            mValueAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {

                }

                override fun onAnimationEnd(p0: Animator) {

                }

                override fun onAnimationCancel(p0: Animator) {

                }

                override fun onAnimationRepeat(p0: Animator) {

                }

            })
            mValueAnimator.start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(mSizeProgressBar, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(mSizeProgressBar, MeasureSpec.EXACTLY)
        )
    }
    fun show() {
        if (visibility != VISIBLE) {
            mValueAnimator.duration = 500
            mValueAnimator.repeatCount = ValueAnimator.INFINITE
            mValueAnimator.start()
            isVisible = true
        }
    }

    fun hide() {
        isVisible = false
        mValueAnimator.cancel()
    }

    fun disappear() {
        visibility = View.GONE
        mValueAnimator.cancel()
    }

}