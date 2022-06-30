package com.example.cleanarchitectureexample.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.login.SignDialogFragment
import com.example.domain.model.config.ConfigDataModel
import com.example.domain.model.login.LoginDataModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    var configData: ConfigDataModel? = null
    var loginData: LoginDataModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.mainViewModel = viewModel
        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        initViewModelCallback()
        viewModel.getConfig()
    }

    private fun initViewModelCallback() = with(viewModel) {
        lifecycleScope.launch {
            networkError.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                return@collectLatest
            }
        }

        configDataModel.observeOnLifecycle(this@MainActivity) { data ->
            configData = data
        }

        isLogin.observeOnLifecycle(this@MainActivity) { data ->
            if (loginData == null && data == null) return@observeOnLifecycle
            Toast.makeText(
                this@MainActivity,
                if (data != null) "${data?.loginInfo?.userInfo?.nick} 님이 로그인에 성공하였습니다."
                else "${loginData?.loginInfo?.userInfo?.nick} 님이 로그아웃 하였습니다.",
                Toast.LENGTH_SHORT
            ).show()
            loginData = data
        }

        loginClickChannel.onEach {
            SignDialogFragment.newInstance().apply {
                dialog?.setOnDismissListener {
                    supportFragmentManager.beginTransaction().remove(this)
                }
                show(supportFragmentManager, "SignDialog")
            }
        }.observeInLifecycle(this@MainActivity)
    }
}