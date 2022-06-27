package com.example.cleanarchitectureexample.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.domain.repository.ConfigRepository
import dagger.hilt.EntryPoints
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit

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
    }

    private fun initViewModelCallback() = with(viewModel) {
        lifecycleScope.launch {
            networkError.collectLatest {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                return@collectLatest
            }
        }

        lifecycleScope.launch {
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
    }
}