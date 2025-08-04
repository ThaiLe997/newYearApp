package com.example.lixinewyear.presentation.test

import android.view.LayoutInflater
import com.example.lixinewyear.databinding.ActivityTestBinding
import com.example.lixinewyear.framework.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestActivity : BaseActivity<TestViewModel, ActivityTestBinding>() {
    override val mViewModel: TestViewModel
            by viewModel()
    override val setLayoutInflater: (LayoutInflater) -> ActivityTestBinding
        get() = ({
            ActivityTestBinding.inflate(it)
        })

    override fun initView() {
    }

    override fun loadData() {
    }
}