package com.example.lixinewyear.presentation.InputDataMoney

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.lixinewyear.databinding.ItemInputDataMoneyBinding
import com.example.lixinewyear.framework.base.adapter.BaseAdapter
import com.example.lixinewyear.framework.base.adapter.BaseRecyclerViewHolder
import com.example.lixinewyear.framework.model.MoneyPackage

class InputDataMoneyAdapter(context: Context) :
    BaseAdapter<MoneyPackage, ItemInputDataMoneyBinding>(context) {
    override fun setLayout(viewType: Int): (LayoutInflater, ViewGroup?, Boolean) -> ItemInputDataMoneyBinding =
        { layoutInflater, viewGroup, b ->
            ItemInputDataMoneyBinding.inflate(layoutInflater, viewGroup, b)
        }

    override fun setViewHolder(
        binding: ItemInputDataMoneyBinding,
        viewType: Int
    ): BaseRecyclerViewHolder =
        InputDataMoneyViewHolder(binding)

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        (holder as? InputDataMoneyViewHolder)?.bindData(position)
    }

    inner class InputDataMoneyViewHolder(val binding: ItemInputDataMoneyBinding) :
        BaseRecyclerViewHolder(binding) {
        fun bindData(position: Int) {
            val model = getItem(position)
//            binding.txtValueMoney.text = model.value
        }
    }
}