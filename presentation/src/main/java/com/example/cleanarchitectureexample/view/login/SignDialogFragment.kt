package com.example.cleanarchitectureexample.view.login

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.databinding.DialogSignBinding
import com.example.cleanarchitectureexample.utils.Common
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import com.example.cleanarchitectureexample.view.main.MainViewModel
import com.example.cleanarchitectureexample.view.webViewAct.WebViewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
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
    ): View {
        mBinding = DialogSignBinding.inflate(layoutInflater, container, false)
        mBinding.signViewModel = viewModel
        mBinding.loginLayout.signViewModel = viewModel
        mBinding.joinLayout.signViewModel = viewModel
        mBinding.lifecycleOwner = viewLifecycleOwner
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.configApp.value?.let {
            viewModel.configApp.value = it

            viewModel.kakao = it.socialLogin.getValue("KAKAO")
            viewModel.naver = it.socialLogin.getValue("NAVER")
            viewModel.google = it.socialLogin.getValue("GOOGLE")
            viewModel.facebook = it.socialLogin.getValue("FACEBOOK")
        }

        initViewModelCallback()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Dialog(requireContext(), R.style.NewDialog)
    }

    private fun initViewModelCallback() = with(viewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            closeDialog.collectLatest {
                dismiss()
            }
        }

        loginSuccess.onEach { model ->
            model?.let {
                mainViewModel.loginModel.value = MainViewModel.LoginState.Ok(it)

                Common.showSnackBar(
                    mBinding.root,
                    "${model.loginInfo.userInfo.nick} 님이 로그인에 성공하였습니다."
                )
            }

            dismiss()
        }.observeInLifecycle(viewLifecycleOwner)

        joinSuccess.onEach {
            Common.showSnackBar(mBinding.root, resources.getString(R.string.join_success))
        }.observeInLifecycle(viewLifecycleOwner)

        networkError.onEach {
            Common.showSnackBar(mBinding.root, it)
        }.observeInLifecycle(viewLifecycleOwner)

        actionWebView.onEach {
            it?.let {
                val link = configApp.value?.link ?: return@onEach
                when (it.id) {
                    R.id.findId -> {
                        startActivity(WebViewActivity.getExternalBrowserIntent(link.findId))
                    }

                    R.id.findPw -> {
                        startActivity(WebViewActivity.getExternalBrowserIntent(link.findPw))
                    }

                    R.id.agree1 -> {
                        startActivity(
                            WebViewActivity.getInAppBrowserIntent(
                                activity = requireActivity(),
                                url = link.policyService,
                                pageTitle = "서비스 이용약관"
                            )
                        )
                    }

                    R.id.agree2 -> {
                        startActivity(
                            WebViewActivity.getInAppBrowserIntent(
                                activity = requireActivity(),
                                url = link.policyPrivacy,
                                pageTitle = "개인정보 처리방침"
                            )
                        )
                    }

                    R.id.agree3 -> {
                        startActivity(
                            WebViewActivity.getInAppBrowserIntent(
                                activity = requireActivity(),
                                url = "https://www.pandalive.co.kr/policy/youth",
                                pageTitle = "청소년 보호정책"
                            )
                        )
                    }

                    R.id.agree4 -> {
                        startActivity(
                            WebViewActivity.getInAppBrowserIntent(
                                activity = requireActivity(),
                                url = link.policyMarketing,
                                pageTitle = "광고성 정보이용 약관"
                            )
                        )
                    }
                }
            }
        }.observeInLifecycle(viewLifecycleOwner)
    }

    companion object {
        fun newInstance(): SignDialogFragment = SignDialogFragment()
    }
}