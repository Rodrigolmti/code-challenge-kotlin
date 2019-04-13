package com.arctouch.codechallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentHomeBinding
import com.arctouch.codechallenge.ui.MainActivity
import com.arctouch.codechallenge.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.viewmodel.ext.android.viewModel

const val PAGE_SPAN_COUNT = 3

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {

    private val viewModel by viewModel<HomeViewModel>()

    private var homeMovieAdapter: HomeMovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindContentToViewModel(inflater, viewModel, container, R.layout.fragment_home)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.moviesLiveData.removeObservers(this)
        homeMovieAdapter = null
    }

    private fun init() {
        homeMovieAdapter = HomeMovieAdapter { movie -> navigate(HomeFragmentDirections.actionGoToDetail(movie)) }
        viewModel.moviesLiveData.observe(this, Observer {
            homeMovieAdapter?.addAll(it)
        })
        (activity as MainActivity).queryString.observe(this, Observer { query ->
            viewModel.searchMovies(query)
        })
        configRecyclerView()
    }

    private fun configRecyclerView() {
        val layoutManager = GridLayoutManager(this.context, PAGE_SPAN_COUNT)
        mBinding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setLayoutManager(layoutManager)
            adapter = homeMovieAdapter
        }
        recyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        homeMovieAdapter?.configAdapterPageHandler(layoutManager) {
            viewModel.getUpcomingMovies()
        }?.let { mBinding.recyclerView.addOnScrollListener(it) }
    }
}