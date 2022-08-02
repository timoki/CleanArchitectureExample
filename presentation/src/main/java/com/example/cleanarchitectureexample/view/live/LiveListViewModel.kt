package com.example.cleanarchitectureexample.view.live

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.model.live.LiveFilterType
import com.example.domain.usecase.live.RequestGetLiveRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveListViewModel @Inject constructor(
    private val requestGetLiveRemoteUseCase: RequestGetLiveRemoteUseCase
): ViewModel() {

    enum class ViewState {
        NOT_EMPTY_ERROR, ERROR, VIEW, LOADING, EMPTY
    }

    val viewState = MutableStateFlow(ViewState.LOADING)

    val isTopButtonVisible = MutableStateFlow(false)

    private val _sortingType = MutableStateFlow(LiveFilterType.Sorting.SORTING_NEW)
    val sortingType
        get() = _sortingType.asStateFlow()

    val adultType = MutableStateFlow(LiveFilterType.AdultFilter.HIDE_ADULT_LIVE)

    private val _retryClickChannel = Channel<Unit>(Channel.CONFLATED)
    val retryClickChannel = _retryClickChannel.receiveAsFlow()

    val getAllLive =
        requestGetLiveRemoteUseCase(20, sortingType, adultType).cachedIn(scope = viewModelScope)

    fun filterClick(type: LiveFilterType.Sorting) {
        _sortingType.value = type
    }

    fun retryClick() = viewModelScope.launch {
        _retryClickChannel.send(Unit)
    }
}