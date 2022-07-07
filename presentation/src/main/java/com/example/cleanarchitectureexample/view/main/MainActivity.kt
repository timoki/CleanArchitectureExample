package com.example.cleanarchitectureexample.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.login.SignDialogFragment
import com.example.cleanarchitectureexample.view.main.adapter.LiveListDataAdapter
import com.example.cleanarchitectureexample.view.main.adapter.PagingLoadStateAdapter
import com.example.data.db.database.DataStoreModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy {
        LiveListDataAdapter()
    }

    private val header: PagingLoadStateAdapter by lazy {
        PagingLoadStateAdapter { adapter.retry() }
    }

    private val footer: PagingLoadStateAdapter by lazy {
        PagingLoadStateAdapter { adapter.retry() }
    }

    @Inject
    lateinit var dataStore: DataStoreModule

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val permissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.all { permissions -> permissions.value }) {
                Toast.makeText(this, "권한 설정 완료", Toast.LENGTH_SHORT).show()
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.mainViewModel = viewModel
        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        if (!checkPermission(permissions)) {
            permissionResultLauncher.launch(permissions)
        }

        adapter.apply {
            mBinding.list.adapter = withLoadStateHeaderAndFooter(
                header = header,
                footer = footer
            )

            viewModel.getAllLive.observeOnLifecycle(this@MainActivity) {
                adapter.submitData(it)
            }

            loadStateFlow.observeOnLifecycle(this@MainActivity) { loadState ->
                mBinding.refresh.isRefreshing =
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
                            if (adapter.itemCount <= 0) MainViewModel.ViewState.ERROR
                            else MainViewModel.ViewState.NOT_EMPTY_ERROR
                    }

                    loadingState != null -> {
                        viewModel.viewState.value = MainViewModel.ViewState.LOADING
                    }

                    notLoadingState != null -> {
                        viewModel.viewState.value =
                            if (adapter.itemCount <= 0) MainViewModel.ViewState.EMPTY
                            else MainViewModel.ViewState.VIEW
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getConfig()
        }

        initListener()
        initViewModelCallback()
    }

    private fun initListener() = with(mBinding) {
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

        mBinding.refresh.setOnRefreshListener {
            if (mBinding.refresh.isRefreshing) {
                mBinding.refresh.isRefreshing = false
            }

            adapter.refresh()
        }
    }

    private fun initViewModelCallback() = with(viewModel) {
        loginModel.observeOnLifecycle(this@MainActivity) { type ->
            isLogin.value = type is MainViewModel.LoginState.Ok
        }

        networkState.onEach {
            Toast.makeText(
                this@MainActivity,
                it.second,
                Toast.LENGTH_LONG
            ).show()

            return@onEach
        }.observeInLifecycle(this@MainActivity)

        loginClickChannel.onEach {
            SignDialogFragment.newInstance().apply {
                dialog?.setOnDismissListener {
                    supportFragmentManager.beginTransaction().remove(this)
                }

                show(supportFragmentManager, "SignDialog")
            }
        }.observeInLifecycle(this@MainActivity)

        lifecycleScope.launch(Dispatchers.IO) {
            if (dataStore.isAutoLogin().first()) {
                viewModel.login()
            }
        }

        sortingType.observeOnLifecycle(this@MainActivity) {
            mBinding.list.scrollToPosition(0)
            adapter.refresh()
        }

        adultType.observeOnLifecycle(this@MainActivity) {
            mBinding.list.scrollToPosition(0)
            adapter.refresh()
        }
    }

    private fun checkPermission(permissions: Array<String>): Boolean = permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}