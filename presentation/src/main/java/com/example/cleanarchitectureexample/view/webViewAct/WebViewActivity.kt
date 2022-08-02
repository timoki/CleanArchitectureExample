package com.example.cleanarchitectureexample.view.webViewAct

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.databinding.ActivityWebViewBinding
import com.example.cleanarchitectureexample.utils.observeInLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WebViewActivity : AppCompatActivity() {

    private val mBinding: ActivityWebViewBinding by lazy {
        ActivityWebViewBinding.inflate(layoutInflater)
    }

    private val viewModel: WebViewActViewModel by viewModels()

    companion object {
        private const val TAG = "WebViewActivity"
        private const val DESKTOP_USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36"
        private const val INTENT_PROTOCOL_START = "intent:"
        private const val INTENT_PROTOCOL_INTENT = "#Intent;"
        private const val INTENT_PROTOCOL_END = ";end;"
        private const val GOOGLE_PLAY_STORE_PREFIX = "market://details?id="

        private const val PAGE_URL = "PAGE_URL"
        private const val PAGE_TITLE = "PAGE_TITLE"
        private const val PAGE_SHOWTITLE = "PAGE_SHOWTITLE"

        fun getInAppBrowserIntent(
            activity: Activity,
            url: String,
            pageTitle: String = "",
            showTitle: Boolean = true
        ): Intent {
            return Intent(activity, WebViewActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(PAGE_URL, url)
                putExtra(PAGE_TITLE, pageTitle)
                putExtra(PAGE_SHOWTITLE, showTitle)
            }
        }

        fun getExternalBrowserIntent(url: String): Intent {
            return Intent(Intent.ACTION_VIEW, Uri.parse(url)).addCategory(Intent.CATEGORY_BROWSABLE)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding.webViewViewModel = viewModel
        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        mBinding.title = intent.getStringExtra(PAGE_TITLE)
        mBinding.showTitle = intent.getBooleanExtra(PAGE_SHOWTITLE, false)

        intent.getStringExtra(PAGE_URL)?.let {
            initWebViewSetting(it)
        }

        initViewModelCallback()
    }

    private fun initViewModelCallback() = with(viewModel) {
        backClick.onEach {
            finish()
        }.observeInLifecycle(this@WebViewActivity)
    }

    private fun initWebViewSetting(url: String) = with(mBinding) {
        webView.settings.apply {
            loadWithOverviewMode = true
            useWideViewPort = true
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
            userAgentString = DESKTOP_USER_AGENT
            mediaPlaybackRequiresUserGesture = false
            domStorageEnabled = true
            loadWithOverviewMode = true
        }

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        webView.webViewClient = webViewClient
        webView.webChromeClient = webViewChromeClient

        CoroutineScope(Dispatchers.Main).launch {
            webView.loadUrl(url)
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            viewModel.isShowProgress.value = true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            viewModel.isShowProgress.value = false
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            val builder = AlertDialog.Builder(this@WebViewActivity)
            builder.setPositiveButton("확인") { _, _ -> finish() }

            when (error?.errorCode) {
                ERROR_AUTHENTICATION // 서버에서 사용자 인증 실패
                -> builder.setMessage("서버에서 사용자 인증을 실패하였습니다.\nERROR_CODE : ERROR_AUTHENTICATION")
                ERROR_BAD_URL // 잘못된 URL
                -> builder.setMessage("URL이 올바르지 않습니다.\nERROR_CODE : ERROR_BAD_URL")
                ERROR_CONNECT // 서버로 연결 실패
                -> builder.setMessage("서버로 연결을 실패하였습니다.\nERROR_CODE : ERROR_CONNECT")
                ERROR_FAILED_SSL_HANDSHAKE // SSL handshake 수행 실패
                -> builder.setMessage("SSL handshake 수행을 실패하였습니다..\nERROR_CODE : ERROR_FAILED_SSL_HANDSHAKE")
                ERROR_FILE // 일반 파일 오류
                -> builder.setMessage("일반 파일 오류입니다.\nERROR_CODE : ERROR_FILE")
                ERROR_FILE_NOT_FOUND // 파일을 찾을 수 없습니다
                -> builder.setMessage("파일을 찾을 수 없습니다.\nERROR_CODE : ERROR_FILE_NOT_FOUND")
                ERROR_HOST_LOOKUP // 서버 또는 프록시 호스트 이름 조회 실패
                -> builder.setMessage("서버 또는 프록시 호스트 이름 조회를 실패하였습니다.\nERROR_CODE : ERROR_HOST_LOOKUP")
                ERROR_IO // 서버에서 읽거나 서버로 쓰기 실패
                -> builder.setMessage("서버에서 읽거나 서버로 쓰기 실패하였습니다.\nERROR_CODE : ERROR_IO")
                ERROR_PROXY_AUTHENTICATION // 프록시에서 사용자 인증 실패
                -> builder.setMessage("프록시 사용자 인증을 실패하였습니다.\nERROR_CODE : ERROR_PROXY_AUTHENTICATION")
                ERROR_REDIRECT_LOOP // 너무 많은 리디렉션
                -> builder.setMessage("리디렉션을 초과하였습니다.\nERROR_CODE : ERROR_REDIRECT_LOOP")
                ERROR_TIMEOUT // 연결 시간 초과
                -> builder.setMessage("연결 시간을 초과하였습니다.\nERROR_CODE : ERROR_TIMEOUT")
                ERROR_TOO_MANY_REQUESTS // 페이지 로드중 너무 많은 요청 발생
                -> builder.setMessage("너무 많은 요청이 발생하였습니다.\nERROR_CODE : ERROR_TOO_MANY_REQUESTS")
                ERROR_UNKNOWN // 일반 오류
                -> builder.setMessage("일반적 오류입니다.\nERROR_CODE : ERROR_UNKNOWN")
                ERROR_UNSUPPORTED_AUTH_SCHEME // 지원되지 않는 인증 체계
                -> builder.setMessage("지원되지 않는 인증 체계입니다.\nERROR_CODE : ERROR_UNSUPPORTED_AUTH_SCHEME")
                ERROR_UNSUPPORTED_SCHEME // URI가 지원되지 않는 방식
                -> builder.setMessage("URI가 지원되지 않는 방식입니다.\nERROR_CODE : ERROR_UNSUPPORTED_SCHEME")
                else -> builder.setMessage("원인을 알수 없는 오류입니다.\n네트워크 상태를 확인하시거나 나중에 다시 시도 해 주세요.\nERROR_CODE : ERROR_UNKNOWN")
            }

            builder.setTitle(R.string.app_name)
            builder.setCancelable(false) // 뒤로가기 버튼 차단
            builder.show() // 다이얼로그실행
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val overrideUrl = request?.url.toString()
            if (overrideUrl.startsWith(INTENT_PROTOCOL_START)) {
                val customUrlStartIndex = INTENT_PROTOCOL_START.length
                val customUrlEndIndex = overrideUrl.indexOf(INTENT_PROTOCOL_INTENT)

                if (customUrlEndIndex < 0) {
                    return false
                } else {
                    val customUrl =
                        overrideUrl.substring(customUrlStartIndex, customUrlEndIndex)
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(customUrl)
                            ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    } catch (e: ActivityNotFoundException) {
                        val packageStartIndex =
                            customUrlEndIndex + INTENT_PROTOCOL_INTENT.length
                        val packageEndIndex = overrideUrl.indexOf(INTENT_PROTOCOL_END)

                        val packageName = overrideUrl.substring(
                            packageStartIndex,
                            if (packageEndIndex < 0) overrideUrl.length else packageEndIndex
                        )
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(GOOGLE_PLAY_STORE_PREFIX + packageName)
                            ).addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK
                            )
                        )
                    }

                    return true
                }
            }

            return !overrideUrl.startsWith("https://")
        }
    }

    private val webViewChromeClient = object : WebChromeClient() {
        override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
            Log.d(
                TAG,
                "${consoleMessage.message()}\n${consoleMessage.messageLevel()}\n${consoleMessage.sourceId()}"
            )
            return super.onConsoleMessage(consoleMessage)
        }

        override fun onPermissionRequest(request: PermissionRequest) {
            request.grant(request.resources)
        }

        override fun onGeolocationPermissionsShowPrompt(
            origin: String,
            callback: GeolocationPermissions.Callback
        ) {
            Log.d(TAG, "onGeolocationPermissionsShowPrompt")
            super.onGeolocationPermissionsShowPrompt(origin, callback)
            callback.invoke(origin, true, false)
        }

        override fun onCreateWindow(
            view: WebView,
            dialog: Boolean,
            userGesture: Boolean,
            resultMsg: Message
        ): Boolean {
            Log.d(TAG, "onCreateWindow")
            val newWebView = WebView(this@WebViewActivity)

            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = newWebView
            resultMsg.sendToTarget()

            return true
        }

        override fun onJsAlert(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            Log.d(TAG, "onJsAlert -> $url")
            AlertDialog.Builder(view.context).apply {
                setTitle(getString(R.string.app_name))
                setMessage(message)
                setPositiveButton(android.R.string.ok) { dialog, _ ->
                    result.confirm()
                    dialog.dismiss()
                }
                setCancelable(false)
                create()
                show()
            }

            return true
        }

        override fun onJsConfirm(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            Log.d(TAG, "onJsConfirm -> $url")
            AlertDialog.Builder(view.context).apply {
                setTitle(getString(R.string.app_name))
                setMessage(message)
                setPositiveButton(android.R.string.ok) { dialog, _ ->
                    result.confirm()
                    dialog.dismiss()
                }
                setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    result.cancel()
                    dialog.dismiss()
                }
                create()
                show()
            }

            return true
        }
    }

    override fun onResume() {
        super.onResume()
        mBinding.webView.onResume()
        mBinding.webView.resumeTimers()
    }

    override fun onPause() {
        mBinding.webView.pauseTimers()
        mBinding.webView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mBinding.webView.stopLoading()
        mBinding.webView.destroy()
        super.onDestroy()
    }
}