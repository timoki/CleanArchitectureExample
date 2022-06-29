package com.example.cleanarchitectureexample.view.login

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.databinding.DialogSignBinding
import com.example.cleanarchitectureexample.utils.DataStoreModule
import com.example.cleanarchitectureexample.view.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignDialogFragment : DialogFragment() {

    private lateinit var mBinding: DialogSignBinding

    private val viewModel: SignDialogViewModel by viewModels()

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogSignBinding.inflate(layoutInflater, container, false)
        mBinding.signViewModel = viewModel
        mBinding.loginLayout.signViewModel = viewModel
        mBinding.joinLayout.signViewModel = viewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelCallback()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.NewDialog)
    }

    private fun initViewModelCallback() = with(viewModel) {
        val a = ""
        if (a.isNullOrEmpty())
        lifecycleScope.launch {
            loginSuccess.collectLatest {
                context?.let { context ->
                    if (isAutoLoginChecked.value) { //자동 로그인 체크 시 아이디, 비번 저장함
                        DataStoreModule(context)
                            .saveAccountInfo(
                                loginIdText.value,
                                loginPwText.value
                            )
                        return@let
                    }
                    if (isIdSaveChecked.value) {
                        DataStoreModule(context)
                            .saveMemoryId(
                                loginIdText.value
                            )
                    }
                }
                mainViewModel.isLogin.value = true
                dismiss()
            }

            networkError.collectLatest {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}