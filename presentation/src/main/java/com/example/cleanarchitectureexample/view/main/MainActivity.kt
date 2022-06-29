package com.example.cleanarchitectureexample.view.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.view.login.SignDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

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

        lifecycleScope.launch { // StateFlow 구독은 자동 Distinct 되기 때문에 이전 값과 똑같은 값이면 collectLatest 를 타지 않는다.
            configDataModel.collectLatest { data ->
                data?.message?.let {
                    Toast.makeText(
                        this@MainActivity,
                        "result : ${data.result} & message : ${data.message}", Toast.LENGTH_LONG
                    ).show()
                }
                return@collectLatest
            }
        }

        lifecycleScope.launch {
            loginClickChannel.collectLatest {
                SignDialogFragment().show(supportFragmentManager, "")
                return@collectLatest
            }
        }
    }
}