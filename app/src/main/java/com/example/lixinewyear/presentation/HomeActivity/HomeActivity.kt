package com.example.lixinewyear.presentation.HomeActivity

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import com.appota.lunarcore.LunarCoreHelper
//import com.appota.lunarcore.LunarCoreHelper
import com.example.lixinewyear.databinding.ActivityHomeBinding
import com.example.lixinewyear.framework.base.BaseActivity
import com.example.lixinewyear.framework.common.AppNavigation
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar
import java.util.TimeZone

class HomeActivity : BaseActivity<HomeViewModel, ActivityHomeBinding>()  {
    override val mViewModel: HomeViewModel
            by viewModel()

    override val setLayoutInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ({
            ActivityHomeBinding.inflate(it)
        })

    private val startForResultInputDataMoney =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // TODO: handle result call back
            }
        }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"))
        val currentLunarCalendar = LunarCoreHelper.convertSolar2Lunar(
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR),
            7.0
        )

        Log.d("LOL", "currentLunarCalendar: ${currentLunarCalendar} ")
        Log.d("LOL", "mCalendar: ${calendar.timeInMillis} ")

        binding.txtLunarDate.text = currentLunarCalendar[0].toString() + "/" + currentLunarCalendar[1].toString() + "/" + currentLunarCalendar[2].toString()
        binding.txtDate.text = currentLunarCalendar[0].toString() + "/" + currentLunarCalendar[1].toString() + "/" + currentLunarCalendar[2].toString()

        binding.btnInputData.setOnClickListener {
            AppNavigation.inputDataMoneyDirection(this, startForResultInputDataMoney)
        }


    }

    override fun loadData() {
    }


}