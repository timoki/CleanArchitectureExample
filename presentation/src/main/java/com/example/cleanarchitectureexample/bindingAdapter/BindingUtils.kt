package com.example.cleanarchitectureexample.bindingAdapter

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.example.cleanarchitectureexample.utils.FontsTypes

object BindingUtils {
    @BindingAdapter("font")
    @JvmStatic
    fun TextView.font(type: FontsTypes) {
        try {
            typeface = ResourcesCompat.getFont(context, type.fontRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}