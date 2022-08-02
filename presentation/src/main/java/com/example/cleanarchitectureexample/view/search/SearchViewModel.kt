package com.example.cleanarchitectureexample.view.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.ResourceProvider
import com.example.domain.usecase.search.RequestGetLiveSearchRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val requestGetLiveSearchRemoteUseCase: RequestGetLiveSearchRemoteUseCase,
    private val resource: ResourceProvider
) : ViewModel() {

    private val _networkState = Channel<Pair<Boolean, String>>(Channel.CONFLATED)
    val networkState = _networkState.receiveAsFlow()

    private val _backClickChannel = Channel<Unit>(Channel.CONFLATED)
    val backClickChannel = _backClickChannel.receiveAsFlow()

    val searchText = MutableStateFlow("")

    val searchLive =
        requestGetLiveSearchRemoteUseCase(20, searchText.value).cachedIn(scope = viewModelScope)

    fun backClick() = viewModelScope.launch {
        _backClickChannel.send(Unit)
    }

    fun searchClick() = viewModelScope.launch {
        if (searchText.value.length < 2) {
            _networkState.send(false to resource.getString(R.string.search_shot_length))
            return@launch
        }
    }
}