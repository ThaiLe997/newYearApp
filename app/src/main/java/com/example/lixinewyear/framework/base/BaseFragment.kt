package com.example.lixinewyear.framework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.lixinewyear.R
import com.example.lixinewyear.presentation.custom.BurnLoadingView

abstract class BaseFragment<out T: BaseViewModel, VB: ViewBinding>: Fragment(), View.OnClickListener {

    abstract val mViewModel: T

    private lateinit var _binding: VB
    protected val binding: VB
        get() = _binding

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    private var mViewContents: View? = null
    private var mViewMask: BurnLoadingView? = null

    internal lateinit var viewRoot: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        _binding.root.let {
            viewRoot = it
        }
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewMask(view)
        setupViewContent()
        initView()
        loadData()
    }

    private fun setupViewMask(viewRoot: View) {
        mViewMask = viewRoot.findViewById(R.id.viewMask)
        mViewMask?.setOnClickListener { }
    }

    fun setupViewContent() {
        mViewContents = viewRoot.findViewById(R.id.viewContents)
    }


    abstract fun initView()

    abstract fun loadData()

    override fun onClick(v: View?) {

    }
}