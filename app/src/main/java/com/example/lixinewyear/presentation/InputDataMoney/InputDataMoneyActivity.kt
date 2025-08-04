package com.example.lixinewyear.presentation.InputDataMoney

import android.view.LayoutInflater
import com.example.lixinewyear.databinding.ActivityInputDataMoneyBinding
import com.example.lixinewyear.framework.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class InputDataMoneyActivity: BaseActivity<InputDataMoneyViewModel, ActivityInputDataMoneyBinding>() {
    override val mViewModel: InputDataMoneyViewModel
        by viewModel()
    override val setLayoutInflater: (LayoutInflater) -> ActivityInputDataMoneyBinding
        get() = ({
            ActivityInputDataMoneyBinding.inflate(it)
        })

    override fun initView() {

    }

    override fun loadData() {

    }
}