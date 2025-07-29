package com.example.lixinewyear.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.example.lixinewyear.R
import com.example.lixinewyear.framework.common.AppUtils
import com.example.lixinewyear.framework.common.localehelper.LocaleAwareCompatActivity
import com.example.lixinewyear.presentation.custom.AppToolBar
import com.example.lixinewyear.presentation.custom.BurnLoadingView
import kotlin.math.roundToInt

abstract class BaseActivity<out T : BaseViewModel, VB : ViewBinding> :
    LocaleAwareCompatActivity() {

    private lateinit var _binding: VB
    protected val binding: VB
        get() = _binding
    abstract val mViewModel: T
    abstract val setLayoutInflater: (LayoutInflater) -> VB
    private var mViewRoot: View? = null
    private var mViewContents: View? = null
    private var mViewMask: BurnLoadingView? = null
    private var mOnStateNetworkActivity: Boolean = true



    abstract fun initView()
    abstract fun loadData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setLayoutInflater(layoutInflater)
        setContentView(binding.root)

        mViewRoot = findViewById(R.id.viewRoot)
        mViewRoot?.setBackgroundColor(getColor(R.color.colorViewRootWhite))
        mViewContents = mViewRoot?.findViewById(R.id.viewContents)
        window.setSoftInputMode(modeInput())
        setupToolBar()
        setupViewMask()
    }

    private fun setupViewMask() {
        mViewMask = findViewById(R.id.viewMask)
        mViewMask?.setOnClickListener { hideViewMaskCallBack() }
        mViewMask?.setBackgroundColor(getColor(R.color.colorViewRootWhite))
    }

    private fun setupToolBar() {
        val mToolBar = findViewById(R.id.toolbarBack) as? AppToolBar
        val layoutParams = mViewRoot?.layoutParams
        val desiredHeight = AppUtils.dpToPx(this, 50f).roundToInt()
        when (layoutParams) {
            RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ),
                -> {
                mToolBar?.layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    desiredHeight
                )
            }

            ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ),
                -> {
                mToolBar?.layoutParams = ConstraintLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    desiredHeight
                )
            }

            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ),
                -> {
                mToolBar?.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    desiredHeight
                )
            }

            FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ),
                -> {
                mToolBar?.layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    desiredHeight
                )
            }
        }

        (mToolBar)?.addEventBack(object :
            AppToolBar.EventToolBar {
            override fun onClickBack() {
                onPressBack()
            }
        })
    }
    open fun onPressBack() {
        finish()
    }

    open fun modeInput() = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
    open fun hideViewMaskCallBack() {}
}