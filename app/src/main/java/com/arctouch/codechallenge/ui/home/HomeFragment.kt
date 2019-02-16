package com.arctouch.codechallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentHomeBinding
import com.arctouch.codechallenge.ui.base.BaseFragment
import com.arctouch.codechallenge.util.setUpWithGridView
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindContentToViewModel(inflater, viewModel, container, R.layout.fragment_home)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies.observe(this, Observer {
            mBinding.recyclerView.setUpWithGridView(HomeMovieAdapter(it) { movie ->
                navigate(HomeFragmentDirections.actionGoToDetail(movie))
            })
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.movies.removeObservers(this)
    }
}