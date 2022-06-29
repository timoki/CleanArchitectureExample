package com.example.cleanarchitectureexample.utils

import androidx.annotation.FontRes
import com.example.cleanarchitectureexample.R

enum class FontsTypes(
    @FontRes val fontRes: Int
) {
    BLACK(R.font.noto_sans_cjk_kr_black),
    BOLD(R.font.noto_sans_cjk_kr_bold),
    DEMI_LIGHT(R.font.noto_sans_cjk_kr_demi_light),
    LIGHT(R.font.noto_sans_cjk_kr_light),
    MEDIUM(R.font.noto_sans_cjk_kr_medium),
    REGULAR(R.font.noto_sans_cjk_kr_regular),
    THIN(R.font.noto_sans_cjk_kr_thin)
}
