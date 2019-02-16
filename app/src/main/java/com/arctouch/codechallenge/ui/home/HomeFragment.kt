package com.arctouch.codechallenge.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.FragmentHomeBinding
import com.arctouch.codechallenge.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

const val PAGE_SPAN_COUNT = 3
const val PAGE_SIZE = 20

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
        configRecyclerScrollingListener(configRecyclerView())
        viewModel.moviesLiveData.observe(this, Observer {
            mBinding.recyclerView.adapter = HomeMovieAdapter(it) { movie ->
                navigate(HomeFragmentDirections.actionGoToDetail(movie))
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.moviesLiveData.removeObservers(this)
    }

    private fun configRecyclerView(): GridLayoutManager {

        val layoutManager = GridLayoutManager(this.context, PAGE_SPAN_COUNT)
        mBinding.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            setLayoutManager(layoutManager)
        }
        return layoutManager
    }

    private fun configRecyclerScrollingListener(layoutManager: GridLayoutManager) {
        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                if (viewModel.loading.get()) return
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                    viewModel.getUpcomingMovies()
                }
            }
        })
    }
}