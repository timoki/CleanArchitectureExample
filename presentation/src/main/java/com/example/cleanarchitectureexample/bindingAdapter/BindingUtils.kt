package com.example.cleanarchitectureexample.bindingAdapter

import android.content.res.ColorStateList
import android.text.TextWatcher
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.FontsTypes
import com.example.cleanarchitectureexample.utils.JoinHelperTypes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("font")
fun TextView.font(type: FontsTypes) {
    typeface = ResourcesCompat.getFont(context, type.fontRes)
}

@BindingAdapter("helperText")
fun TextInputLayout.helperText(type: JoinHelperTypes) {
    helperText = context.getString(type.stringRes)
    val color =
        if (type == JoinHelperTypes.ID_NORMAL || type == JoinHelperTypes.PW_NORMAL || type == JoinHelperTypes.PW_RE_NORMAL || type == JoinHelperTypes.NICK_NORMAL) R.color.text_dark_grey else R.color.sub_color_red
    setHelperTextColor(ResourcesCompat.getColorStateList(context.resources, color, context.theme))
}

@BindingAdapter("textWatcher")
fun TextInputEditText.textWatcher(watcher: TextWatcher) {
    addTextChangedListener(watcher)
}