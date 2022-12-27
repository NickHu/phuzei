package com.alirezaafkar.phuzei.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alirezaafkar.phuzei.R
import com.alirezaafkar.phuzei.data.model.Album
import com.alirezaafkar.phuzei.databinding.ItemAlbumBinding
import com.squareup.picasso.Picasso

/**
 * Created by Alireza Afkar on 16/9/2018AD.
 */
class AlbumAdapter(
    private var album: String?,
    private val listener: (Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)

    private var items = mutableListOf<Album>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(items[position]) {
                binding.name.text = this.title
                binding.tick.isVisible = this.id == album

                val layoutParams = binding.root.layoutParams as StaggeredGridLayoutManager.LayoutParams

                binding.count.text = if (this.id.isEmpty()) {
                    Picasso.get().load(R.drawable.tehran).into(binding.image)
                    layoutParams.isFullSpan = true
                    binding.root.context.getString(R.string.category_description)
                } else {
                    Picasso.get().load(this.coverPhotoUrl).into(binding.image)
                    layoutParams.isFullSpan = false
                    binding.root.context.getString(R.string.items_count, this.itemsCount)
                }

                binding.root.setOnClickListener { listener(this) }
            }
        }
    }

    fun addItems(items: List<Album>) {
        val oldSize = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(oldSize, items.size)
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    fun setAlbum(album: String?) {
        this.album = album
        notifyDataSetChanged()
    }
}
