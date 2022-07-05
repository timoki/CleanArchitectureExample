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
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.login.SignDialogFragment
import com.example.cleanarchitectureexample.view.main.adapter.LiveListDataAdapter
import com.example.data.db.database.DataStoreModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
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
                // 권한이 거부
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.mainViewModel = viewModel
        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        // TODO: 아답터 생성
        mBinding.list.adapter = adapter

        if (!checkPermission(permissions)) {
            permissionResultLauncher.launch(permissions)
        }

        lifecycleScope.launch {
            viewModel.getConfig()
        }

        viewModel.requestGetLive.observeOnLifecycle(this@MainActivity) { data ->
            if (viewModel.isLogin.value == null) return@observeOnLifecycle

            data?.let {
                adapter.submitData(data)
            }
        }

        adapter.loadStateFlow.observeOnLifecycle(this@MainActivity) {

        }

        initViewModelCallback()
    }

    private fun initViewModelCallback() = with(viewModel) {
        lifecycleScope.launch {
            networkError.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                return@collectLatest
            }
        }

        isLogin.observeOnLifecycle(this@MainActivity) { data ->
            if (isLogin.value == null && data == null) return@observeOnLifecycle
            Toast.makeText(
                this@MainActivity,
                if (data != null) "${data.loginInfo.userInfo.nick} 님이 로그인에 성공하였습니다."
                else "${isLogin.value?.loginInfo?.userInfo?.nick} 님이 로그아웃 하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
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
    }

    private fun checkPermission(permissions: Array<String>): Boolean = permissions.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}