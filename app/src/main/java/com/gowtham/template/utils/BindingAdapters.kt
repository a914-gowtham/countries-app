package com.gowtham.template.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.setPadding
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        LogMessage.v("Image url $url")
        Utils.loadImage(view, url)
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


}