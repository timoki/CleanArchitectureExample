package com.example.cleanarchitectureexample.view.main.adapter

import androidx.lifecycle.ViewModel
import com.example.domain.model.live.LiveListModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

class LivelistAdapterViewModel : ViewModel() {

    private val _isAdult = MutableStateFlow(false)
    val isAdult
        get() = _isAdult.asStateFlow()

    private val _isLive = MutableStateFlow(false)
    val isLive
        get() = _isLive.asStateFlow()

    private val _isLock = MutableStateFlow(false)
    val isLock
        get() = _isLock.asStateFlow()

    private val _isMobile = MutableStateFlow(false)
    val isMobile
        get() = _isMobile.asStateFlow()

    private val _type = MutableStateFlow("free")
    val type
        get() = _type.asStateFlow()

    private val _userNick = MutableStateFlow("")
    val userNick
        get() = _userNick.asStateFlow()

    private val _title = MutableStateFlow("")
    val title
        get() = _title.asStateFlow()

    private val _liveThumbnail = MutableStateFlow("")
    val liveThumbnail
        get() = _liveThumbnail.asStateFlow()

    private val _userCnt = MutableStateFlow("")
    val userCnt
        get() = _userCnt.asStateFlow()

    private val _playCnt = MutableStateFlow("")
    val playCnt
        get() = _playCnt.asStateFlow()

    private val _startTime = MutableStateFlow("")
    val startTime
        get() = _startTime.asStateFlow()

    fun setItem(item: LiveListModel) {
        _isAdult.value = item.isAdult
        _isLive.value = item.isLive
        _isLock.value = item.isPw
        _isMobile.value = item.browser != "win"
        _type.value = item.type
        _userNick.value = item.userNick
        _title.value = item.title
        _liveThumbnail.value = item.thumbUrl
        _userCnt.value = item.user.toString()
        _playCnt.value = item.playCnt.toString()
        _startTime.value = getTimeText(item.startTime)
    }

    private fun getTimeText(time: String): String {
        val parseSdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val cal = Calendar.getInstance()
        val startTimeCal = Calendar.getInstance()
        startTimeCal.time = parseSdf.parse(time)

        val diffTime = cal.timeInMillis - startTimeCal.timeInMillis
        val sec: Long = diffTime / 1000
        val min: Long = diffTime / (60 * 1000)
        val hour: Long = diffTime / (60 * 60 * 1000)

        return "$hour:$min:$sec"
    }
}