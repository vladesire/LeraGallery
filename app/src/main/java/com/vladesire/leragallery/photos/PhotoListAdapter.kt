package com.vladesire.leragallery.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vladesire.leragallery.databinding.ListItemPhotoBinding

class PhotoListAdapter(
    diffCallback: DiffUtil.ItemCallback<Photo>,
    private val onItemClicked: ((Photo) -> Unit)? = null
) : PagingDataAdapter<Photo, PhotoViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked)
    }
}


class PhotoViewHolder(
    private val binding: ListItemPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo?, onItemClicked: ((Photo) -> Unit)?) {

        photo?.let {
            binding.image.load(it.uri)
            binding.image.rotation = if (it.isChosen) 45f else 0f
        }

        onItemClicked?.let { callback ->
            binding.image.setOnClickListener { _ ->
                photo?.let { it -> callback(it) }
            }
        }
    }
}