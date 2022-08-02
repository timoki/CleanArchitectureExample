package com.example.cleanarchitectureexample.view.live

import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureexample.base.BaseFragment
import com.example.cleanarchitectureexample.databinding.FragLiveListBinding
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.main.MainViewModel
import com.example.cleanarchitectureexample.view.main.adapter.LiveListDataAdapter
import com.example.cleanarchitectureexample.view.main.adapter.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LiveListFragment : BaseFragment<FragLiveListBinding, LiveListViewModel>() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapter by lazy {
        LiveListDataAdapter()
    }

    private val header: PagingLoadStateAdapter by lazy {
        PagingLoadStateAdapter { adapter.retry() }
    }

    private val footer: PagingLoadStateAdapter by lazy {
        PagingLoadStateAdapter { adapter.retry() }
    }

    override fun init(): Unit = with(binding) {
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter.apply {
            list.adapter = withLoadStateHeaderAndFooter(
                header = header,
                footer = footer
            )

            viewModel.getAllLive.observeOnLifecycle(this@LiveListFragment) {
                adapter.submitData(it)
            }

            loadStateFlow.observeOnLifecycle(this@LiveListFragment) { loadState ->
                refresh.isRefreshing =
                    loadState.source.refresh is LoadState.Loading || loadState.mediator?.refresh is LoadState.Loading

                header.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                    ?: loadState.prepend

                val notLoadingState = loadState.mediator?.append as? LoadState.NotLoading
                    ?: loadState.mediator?.prepend as? LoadState.NotLoading
                    ?: loadState.source.append as? LoadState.NotLoading
                    ?: loadState.source.prepend as? LoadState.NotLoading
                    ?: loadState.append as? LoadState.NotLoading
                    ?: loadState.prepend as? LoadState.NotLoading

                val loadingState = loadState.mediator?.append as? LoadState.Loading
                    ?: loadState.mediator?.prepend as? LoadState.Loading
                    ?: loadState.source.append as? LoadState.Loading
                    ?: loadState.source.prepend as? LoadState.Loading
                    ?: loadState.append as? LoadState.Loading
                    ?: loadState.prepend as? LoadState.Loading

                val errorState = loadState.mediator?.append as? LoadState.Error
                    ?: loadState.mediator?.prepend as? LoadState.Error
                    ?: loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error

                when {
                    errorState != null -> {
                        viewModel.viewState.value =
                            if (adapter.itemCount <= 0) LiveListViewModel.ViewState.ERROR
                            else LiveListViewModel.ViewState.NOT_EMPTY_ERROR
                    }

                    loadingState != null -> {
                        viewModel.viewState.value = LiveListViewModel.ViewState.LOADING
                    }

                    notLoadingState != null -> {
                        viewModel.viewState.value =
                            if (adapter.itemCount <= 0) LiveListViewModel.ViewState.EMPTY
                            else LiveListViewModel.ViewState.VIEW
                    }
                }
            }
        }
    }

    override fun initListener() = with(binding) {
        goTopBtn.setOnClickListener {
            list.smoothScrollToPosition(0)
        }

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val position =
                    (list.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                viewModel.isTopButtonVisible.value = position >= 3
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}
        })

        refresh.setOnRefreshListener {
            if (refresh.isRefreshing) {
                refresh.isRefreshing = false
            }

            adapter.refresh()
        }
    }

    override fun initViewModelCallback(): Unit = with(viewModel) {
        sortingType.observeOnLifecycle(this@LiveListFragment) {
            binding.list.scrollToPosition(0)
            adapter.refresh()
        }

        mainViewModel.adultType.observeOnLifecycle(this@LiveListFragment) {
            viewModel.adultType.value = it
            binding.list.scrollToPosition(0)
            adapter.refresh()
        }

        retryClickChannel.onEach {
            adapter.retry()
        }.observeInLifecycle(this@LiveListFragment)
    }
}