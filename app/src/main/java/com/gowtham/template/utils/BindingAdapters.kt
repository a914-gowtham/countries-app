package com.gowtham.template.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gowtham.template.utils.Utils.loadSvg

object BindingAdapters {

    @BindingAdapter("app:imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {

        imageView.loadSvg(url,12f)
    }

    @BindingAdapter("showOnSuccess")
    @JvmStatic
    fun showOnSuccess(view: LinearLayout, state: LoadState) {
        if(state is LoadState.OnSuccess)
            view.show()
        else
            view.gone()
    }

    @BindingAdapter("showOnLoading")
    @JvmStatic
    fun showOnLoading(view: ProgressBar, state: LoadState) {
        if(state is LoadState.OnLoading)
            view.show()
        else
            view.gone()
    }

    @BindingAdapter("showOnFailure")
    @JvmStatic
    fun showOnFailure(view: View, state: LoadState) {
        if(state is LoadState.OnFailure)
            view.show()
        else
            view.gone()
    }

    @BindingAdapter("app:hideIfEmpty")  // Recommended solution
    @JvmStatic fun hideIfZero(view: View, number: Boolean) {
        view.visibility = if (number) View.GONE else View.VISIBLE
    }

    @BindingAdapter("app:nullableText")  // Recommended solution
    @JvmStatic fun nullableText(txtView: TextView, str: String?) {
        txtView.text=if(str.isNullOrBlank()) "Capital unavailable" else str
    }


}