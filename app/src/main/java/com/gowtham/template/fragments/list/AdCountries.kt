package com.gowtham.template.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.gowtham.template.R
import com.gowtham.template.databinding.RowCountryBinding
import com.gowtham.template.models.Country


class AdCountries :
    ListAdapter<Country, RecyclerView.ViewHolder>(DiffCallbackChats()) {

    private var onItemClickListener: ((Country) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowCountryBinding.inflate(layoutInflater, parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as CountryViewHolder
        viewHolder.bind(getItem(position),onItemClickListener)
    }


    class CountryViewHolder(private val binding: RowCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Country, onItemClickListener: ((Country) -> Unit)?) {
            binding.country = item
            binding.imageView.requestFocus()
            binding.root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }

            binding.imageView.loadSvg(item.flag)
            binding.executePendingBindings()
        }
    }

    fun setOnItemClickListener(listener: (Country) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class DiffCallbackChats : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}
fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(300)
        .data(url)
        .diskCachePolicy(CachePolicy.ENABLED)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}