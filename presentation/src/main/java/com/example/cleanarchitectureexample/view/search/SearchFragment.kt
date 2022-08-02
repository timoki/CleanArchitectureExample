package com.example.cleanarchitectureexample.view.search

import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import com.example.cleanarchitectureexample.base.BaseFragment
import com.example.cleanarchitectureexample.bindingAdapter.setTypeFace
import com.example.cleanarchitectureexample.databinding.FragSearchBinding
import com.example.cleanarchitectureexample.utils.Common
import com.example.cleanarchitectureexample.utils.FontsTypes
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.main.MainViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragSearchBinding, SearchViewModel>() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        viewModel.searchLive.observeOnLifecycle(this@SearchFragment) {
            //adapter.submitData(it)
        }
    }

    override fun initListener() = with(binding) {
        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.view.apply {
                    roof@
                    for (v in this.children) {
                        if (v is TextView) {
                            v.setTypeFace(FontsTypes.BOLD)
                            break@roof
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                tab.view.apply {
                    roof@
                    for (v in this.children) {
                        if (v is TextView) {
                            v.setTypeFace(FontsTypes.REGULAR)
                            break@roof
                        }
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                
            }
        })
    }

    override fun initViewModelCallback(): Unit = with(viewModel) {
        networkState.onEach {
            Common.showSnackBar(binding.root, it.second)
            return@onEach
        }.observeInLifecycle(this@SearchFragment)

        backClickChannel.onEach {
            fragmentPop()
        }.observeInLifecycle(this@SearchFragment)
    }

    override fun onDetach() {
        mainViewModel.isMainContentsVisible.value = true
        super.onDetach()
    }
}