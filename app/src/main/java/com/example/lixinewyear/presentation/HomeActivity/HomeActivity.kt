package com.example.lixinewyear.presentation.HomeActivity

import android.view.LayoutInflater
import com.example.lixinewyear.databinding.ActivityHomeBinding
import com.example.lixinewyear.framework.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>()  {
    override val mViewModel: HomeViewModel
            by viewModel()

    override val setLayoutInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ({
            ActivityHomeBinding.inflate(it)
        })

    override fun initView() {
    }

    override fun loadData() {
    }


}