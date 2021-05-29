package com.gowtham.template.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.gowtham.template.R

fun String.printMeD(){
    Log.d("LetsChat:: ",this)
}

fun Context.toast(msg: String){
   Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(msg: String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun Context.toast(msg: Int){
    Toast.makeText(this,getString(msg),Toast.LENGTH_SHORT).show()
}

fun Context.toastNet(){
    Toast.makeText(this, R.string.err_no_net,Toast.LENGTH_SHORT).show()
}

fun snack(context: Activity,msg: String){
    Snackbar.make(context.findViewById(android.R.id.content),msg,2000).show()
}

fun snackNet(context: Activity){
    Snackbar.make(context.findViewById(android.R.id.content),R.string.err_no_net,2000).show()
}

fun View.gone(){
    this.visibility=View.GONE
}

fun View.show(){
    this.visibility=View.VISIBLE
}

fun View.hide(){
    this.visibility=View.INVISIBLE
}

fun ProgressBar.toggle(show: Boolean){
    if (show)
        this.show()
    else
        this.gone();
}

fun NavController.isValidDestination(destination: Int): Boolean {
    return destination == this.currentDestination!!.id
}

fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.updateList(list: List<T>?) {
    this.submitList(
        if (list == this.currentList) {
            LogMessage.v("Same list")
            list.toList()
        } else {
            LogMessage.v("Not Same list")
            list
        }
    )
}

fun  <T, VH : RecyclerView.ViewHolder> ListAdapter<T,VH>.addRestorePolicy(){
    stateRestorationPolicy =
        RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
}
