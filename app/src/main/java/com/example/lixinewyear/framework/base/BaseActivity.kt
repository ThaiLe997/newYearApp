package com.example.lixinewyear.framework.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding
import com.example.lixinewyear.R
import com.example.lixinewyear.framework.common.AppUtils
import com.example.lixinewyear.framework.common.localehelper.LocaleAwareCompatActivity
import com.example.lixinewyear.presentation.custom.AppToolBar
import com.example.lixinewyear.presentation.custom.BurnLoadingView
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

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

    override fun onPause() {
        super.onPause()
        mOnStateNetworkActivity = true
    }

    override fun attachBaseContext(newBase: Context) {
        val displayMetrics = newBase.resources.displayMetrics
        val configuration = newBase.resources.configuration
        val newContext = if (displayMetrics.densityDpi != DisplayMetrics.DENSITY_DEVICE_STABLE) {
            configuration.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE
            newBase.createConfigurationContext(configuration)
        } else {
            newBase
        }
        super.attachBaseContext(newContext)
    }

    inline fun <reified T : BaseFragment<*, *>> getFragmentAt(): T? {
        return supportFragmentManager.fragments.firstOrNull { it is T && it.isAdded } as? T
    }

    fun getViewRoot(): View? = findViewById(R.id.viewRoot)

    open fun viewMask() {
        mViewContents?.visibility = View.GONE
        mViewMask?.startBurnViewAnimation()
        mViewMask?.visibility = View.VISIBLE
    }

    open fun hideMask() {
        lifecycleScope.launch {
            delay(150)
            try {
                mViewMask?.visibility = View.GONE
                mViewMask?.stopBurnViewAnimation()
                mViewContents?.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }

    fun hideSoftKeyboard() {
        currentFocus?.let { viewFocus ->
            hideSoftKeyboard(viewFocus)
        }
    }

    fun hideSoftKeyboard(viewFocus: View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
            viewFocus.windowToken,
            0
        )
    }

    open fun isHideSoftKeyBoardTouchOutSide(): Boolean {
        return true
    }

    fun showSoftKeyboard() {
        this.currentFocus?.let { viewFocus ->
            showSoftKeyboard(viewFocus)
        }
    }

    open fun commonError() {
        hideMask()
    }

    fun copyToClipBoard(content: String) {
        val clipboard: ClipboardManager? =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("Gonta", content)
        clipboard?.setPrimaryClip(clip)
    }

    private fun showSoftKeyboard(viewFocus: View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
            viewFocus,
            InputMethodManager.SHOW_IMPLICIT
        )
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