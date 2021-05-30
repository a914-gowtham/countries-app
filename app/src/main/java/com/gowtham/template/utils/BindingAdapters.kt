package com.gowtham.template.utils

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gowtham.template.utils.Utils.loadSvg

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun imageUrl(imageView: ImageView, url: String) {
        imageView.loadSvg(url)
    }

    @BindingAdapter("showOnSuccess")
    @JvmStatic
    fun showOnSuccess(view: View, state: LoadState?) {
        if(state!=null && state is LoadState.OnSuccess)
            view.show()
        else
            view.gone()
    }

    @BindingAdapter("showOnLoading")
    @JvmStatic
    fun showOnLoading(view: ProgressBar, state: LoadState?) {
        if(state!=null && state is LoadState.OnLoading)
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


}