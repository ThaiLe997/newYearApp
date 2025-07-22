package com.example.lixinewyear.framework.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.example.lixinewyear.framework.common.localehelper.LocaleAwareCompatActivity

abstract class BaseActivity<out T : BaseViewModel, VB : ViewBinding> :
    LocaleAwareCompatActivity() {

    private lateinit var _binding: VB
    protected val binding: VB
        get() = _binding
    abstract val mViewModel: T
    abstract val setLayoutInflater: (LayoutInflater) -> VB
    abstract fun initView()

    abstract fun loadData()
}