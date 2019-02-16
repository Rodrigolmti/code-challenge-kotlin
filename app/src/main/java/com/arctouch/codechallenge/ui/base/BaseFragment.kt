package com.arctouch.codechallenge.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.arctouch.codechallenge.BR

abstract class BaseFragment<BI : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var mBinding: BI
    private lateinit var mViewModel: VM

    protected fun bindContentToViewModel(
        @NonNull inflater: LayoutInflater, @NonNull viewModel: VM,
        @NonNull container: ViewGroup?, @LayoutRes resource: Int
    ): View {
        mBinding = DataBindingUtil.inflate(inflater, resource, container, false)
        mBinding.setVariable(BR.viewModel, viewModel)
        this.mViewModel = viewModel
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.attachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.detachView()
        mBinding.unbind()
    }

    protected fun navigate(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }
}