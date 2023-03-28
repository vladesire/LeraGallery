package com.vladesire.leragallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vladesire.leragallery.databinding.ListItemPhotoBinding

class PhotoListAdapter(
    private val photos: List<Photo>,
    private val onItemClicked: ((Photo) -> Unit)? = null
) : RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position], onItemClicked)
    }

    override fun getItemCount() = photos.size
}


class PhotoViewHolder(
    private val binding: ListItemPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo, onItemClicked: ((Photo) -> Unit)?) {
        binding.image.load(photo.uri)
        onItemClicked?.let { callback ->
            binding.image.setOnClickListener {
                callback(photo)
            }
        }
    }
}