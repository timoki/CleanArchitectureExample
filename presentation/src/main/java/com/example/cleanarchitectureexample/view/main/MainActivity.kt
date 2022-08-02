package com.example.cleanarchitectureexample.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitectureexample.base.BaseFragment
import com.example.cleanarchitectureexample.databinding.ActivityMainBinding
import com.example.cleanarchitectureexample.utils.Common
import com.example.cleanarchitectureexample.utils.FragmentNavigation
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.utils.observeOnLifecycle
import com.example.cleanarchitectureexample.view.live.LiveListFragment
import com.example.cleanarchitectureexample.view.login.SignDialogFragment
import com.example.cleanarchitectureexample.view.search.SearchFragment
import com.example.data.db.database.DataStoreModule
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity
    : AppCompatActivity(),
    BaseFragment.OnFragmentInteractionListener,
    FragmentNavigation.RootFragmentListener {
    private val mBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mFragmentNavigation: FragmentNavigation

    private val rootFragment: Array<Fragment> by lazy {
        arrayOf(LiveListFragment())
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
                Common.showSnackBar(mBinding.root, "권한 설정 완료")
            } else {
                finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.mainViewModel = viewModel
        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        mFragmentNavigation = FragmentNavigation(
            this@MainActivity,
            supportFragmentManager,
            mBinding.frame.id
        )

        if (!checkPermission(permissions)) {
            permissionResultLauncher.launch(permissions)
        }

        lifecycleScope.launch {
            viewModel.getConfig()
        }

        initViewModelCallback()
    }

    private fun initViewModelCallback() = with(viewModel) {
        loginModel.observeOnLifecycle(this@MainActivity) { type ->
            isLogin.value = type is MainViewModel.LoginState.Ok
            mFragmentNavigation.switchTab(0)
        }

        networkState.onEach {
            Common.showSnackBar(mBinding.root, it.second)
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

        searchClickChannel.onEach {
            viewModel.isMainContentsVisible.value = false
            onFragmentPush(SearchFragment())
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun getRootFragment(index: Int): Fragment {
        return (rootFragment[index] as BaseFragment<*, *>).newInstance(index, 0)
    }

    override fun onFragmentPush(fragment: Fragment) {
        mFragmentNavigation.pushFragment(fragment)
    }

    override fun onFragmentPop() {
        mFragmentNavigation.popFragment()
    }
}