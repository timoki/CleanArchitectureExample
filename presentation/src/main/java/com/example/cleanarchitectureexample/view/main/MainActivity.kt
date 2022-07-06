package com.example.cleanarchitectureexample.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.login.SignDialogFragment
import com.example.cleanarchitectureexample.view.main.adapter.LiveListDataAdapter
import com.example.cleanarchitectureexample.view.main.adapter.PagingLoadStateAdapter
import com.example.data.db.database.DataStoreModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
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

    private val loadStateAdapter by lazy {
        PagingLoadStateAdapter(adapter) { adapter.retry() }
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
            mBinding.refresh.setOnRefreshListener {
                refresh()
            }
            mBinding.list.adapter = withLoadStateHeaderAndFooter(
                header = loadStateAdapter,
                footer = loadStateAdapter
            )

            loadStateFlow.observeOnLifecycle(this@MainActivity) { loadState ->
                mBinding.refresh.isRefreshing =
                    loadState.source.refresh is LoadState.Loading || loadState.mediator?.refresh is LoadState.Loading

                loadStateAdapter.loadState = loadState.mediator
                    ?.refresh
                    ?.takeIf { it is LoadState.Error && adapter.itemCount > 0 }
                    ?: loadState.prepend

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.no_internet),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.getConfig()
        }

        viewModel.liveListModel.observeOnLifecycle(this@MainActivity) { data ->
            if (viewModel.loginModel.value == null) return@observeOnLifecycle

            data?.let {
                adapter.submitData(data)
            }
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

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            }
        })
    }

    private fun initViewModelCallback() = with(viewModel) {
        lifecycleScope.launch {
            networkError.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                return@collectLatest
            }
        }

        loginModel.observeOnLifecycle(this@MainActivity) { data ->
            if (loginModel.value == null && data == null) return@observeOnLifecycle
            Toast.makeText(
                this@MainActivity,
                if (data != null) "${data.loginInfo.userInfo.nick} 님이 로그인에 성공하였습니다."
                else "${loginModel.value?.loginInfo?.userInfo?.nick} 님이 로그아웃 하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
            if (loginModel.value != null) {
                getAllLive()
            }
        }

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

        filterType.observeOnLifecycle(this@MainActivity) {
            dataStore.saveLiveListFilter(it)
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