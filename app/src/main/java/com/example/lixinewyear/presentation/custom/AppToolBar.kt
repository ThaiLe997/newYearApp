package com.example.lixinewyear.presentation.custom

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.lixinewyear.R
import com.google.android.material.appbar.MaterialToolbar

class AppToolBar: MaterialToolbar {

    private var mEventToolbar: EventToolBar? = null

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
        contentInsetStartWithNavigation = 0
        setTitleTextAppearance(context, R.style.toolbarStyle)

        attrs.let { attributeSet ->
            val typedArray =
                context.obtainStyledAttributes(attributeSet, R.styleable.AppToolBar)
            val isHaveValueIconBack =
                typedArray.getBoolean(R.styleable.AppToolBar_registerIconBack, true)
            setupToolbar(typedArray.getInt(R.styleable.AppToolBar_themeView, 0),isHaveValueIconBack)
            typedArray.recycle()
        }

        setNavigationOnClickListener {
            mEventToolbar?.onClickBack()
        }
    }

    fun setupToolbar(theme:Int, isHaveValueIconBack:Boolean){
        when (theme) {
            0 -> {
                setTitleTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                setSubtitleTextColor(ContextCompat.getColor(context, R.color.colorWhite))
                background =
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.background_toolbar,
                        null
                    )
                if (isHaveValueIconBack) {
                    navigationIcon =
                        ContextCompat.getDrawable(context, R.drawable.ic_back_light)
                }
            }
            1 -> {
                setTitleTextColor(ContextCompat.getColor(context, R.color.colorTextDark))
                setSubtitleTextColor(ContextCompat.getColor(context, R.color.colorTextDark))
                if (isHaveValueIconBack) {
                    navigationIcon =
                        ContextCompat.getDrawable(context, R.drawable.ic_back_black)
                }
            }

            else -> {
                setTitleTextColor(ContextCompat.getColor(context, R.color.colorAppBlue))
                setSubtitleTextColor(ContextCompat.getColor(context, R.color.colorAppBlue))
                if (isHaveValueIconBack) {
                    navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_back_blue)
                }
            }
        }
    }

    fun addEventBack(eventToolbar: EventToolBar) {
        mEventToolbar = eventToolbar
    }

    interface EventToolBar {
        fun onClickBack()
    }
}