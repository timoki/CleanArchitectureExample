package com.example.cleanarchitectureexample.bindingAdapter

import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.utils.FontsTypes
import com.example.cleanarchitectureexample.utils.JoinHelperTypes
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("setTypeFace")
fun TextView.setTypeFace(type: FontsTypes = FontsTypes.REGULAR) {
    typeface = ResourcesCompat.getFont(context, type.fontRes)
}

/*@BindingAdapter(value = ["setTypeFace", "font"], requireAll = false)
fun setTypeFace(view: TextView, style: Int, @FontRes resId: Int = R.font.noto_sans_cjk_kr_regular) {
    view.setTypeface(
        ResourcesCompat.getFont(
            view.context, if (resId == 0) {
                R.font.noto_sans_cjk_kr_regular
            } else {
                resId
            }
        ), style
    )
}*/

@BindingAdapter("helperText")
fun TextInputLayout.helperText(type: JoinHelperTypes) {
    helperText = context.getString(type.stringRes)
    val color = if (
        type == JoinHelperTypes.ID_NORMAL ||
        type == JoinHelperTypes.PW_NORMAL ||
        type == JoinHelperTypes.PW_RE_NORMAL ||
        type == JoinHelperTypes.NICK_NORMAL
    ) R.color.text_dark_grey
    else R.color.sub_color_red
    setHelperTextColor(ResourcesCompat.getColorStateList(context.resources, color, context.theme))
}

@BindingAdapter("textWatcher")
fun TextInputEditText.textWatcher(watcher: TextWatcher) {
    addTextChangedListener(watcher)
}

@BindingAdapter("setUriImage")
fun ImageView.setUriImage(uri: String) {
    Glide
        .with(context)
        .load(uri)
        .into(this)
}