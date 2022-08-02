package com.example.cleanarchitectureexample.utils

import android.content.res.Resources
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.cleanarchitectureexample.R
import com.example.cleanarchitectureexample.bindingAdapter.setTypeFace
import com.google.android.material.snackbar.Snackbar

object Common {
    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    private val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    private var mSnack: Snackbar? = null

    fun showSnackBar(view: View, message: String) {
        mSnack?.dismiss()
        mSnack = null

        mSnack = Snackbar.make(view, message, Snackbar.LENGTH_SHORT).apply {
            setTextColor(ContextCompat.getColor(context, R.color.white))
            (this.view as ViewGroup).apply {
                setBackgroundResource(R.drawable.snack_bar_background)
                val params = (layoutParams as FrameLayout.LayoutParams).apply {
                    setMargins(16.dp, 0, 16.dp, 20.dp)
                }

                layoutParams = params

                findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                    gravity = Gravity.CENTER_HORIZONTAL
                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                    setTypeFace(FontsTypes.REGULAR)
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }
        }

        mSnack?.show()
    }
}