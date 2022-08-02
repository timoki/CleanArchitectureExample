package com.example.domain.model.live

sealed class LiveFilterType {

    enum class Sorting(val value: String) {
        SORTING_BEST("scoreLiveRecomWeek"),
        SORTING_NEW("startDateTime"),
        SORTING_VIEW("userCnt")
    }

    enum class AdultFilter(val value: String) {
        VIEW_ADULT_LIVE("Y"),
        HIDE_ADULT_LIVE("N")
    }
}