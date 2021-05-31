package com.gowtham.template.fragments.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gowtham.template.databinding.RowInfoBinding
import com.gowtham.template.models.Info
import com.gowtham.template.utils.Utils.clearNull


class AdInfo :
    ListAdapter<Info, RecyclerView.ViewHolder>(DiffCallbackInfo()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowInfoBinding.inflate(layoutInflater, parent, false)
        return InfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as InfoViewHolder
        viewHolder.bind(getItem(position))
    }


    class InfoViewHolder(private val binding: RowInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Info) {
            binding.txtIcon.text = item.icon
            binding.txtTitle.text = item.title
            binding.txtDetail.text = clearNull(item.detail)

            binding.executePendingBindings()
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}


class DiffCallbackInfo : DiffUtil.ItemCallback<Info>() {
    override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean {
        return oldItem.detail == oldItem.detail
    }

    override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean {
        return oldItem == newItem
    }
}
