package com.example.cleanarchitectureexample.view.webViewAct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewActViewModel @Inject constructor() : ViewModel() {

    val isShowProgress = MutableStateFlow(false)

    private val _backClick = Channel<Unit>(Channel.CONFLATED)
    val backClick = _backClick.receiveAsFlow()

    fun onBackClick() = viewModelScope.launch {
        _backClick.send(Unit)
    }
}