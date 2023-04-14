package com.vladesire.leragallery.photos

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vladesire.leragallery.databinding.ListItemPhotoBinding

private const val SYSTEM_PHOTO = 1
private const val SAVED_PHOTO = 2

class SystemPhotoListAdapter(
    diffCallback: DiffUtil.ItemCallback<Photo>,
    private val onItemClicked: ((Photo) -> Unit)? = null
) : PagingDataAdapter<Photo, PhotoViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(SYSTEM_PHOTO, binding)
    }
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClicked) {
            notifyItemChanged(position, SystemPhotoItemAnimator.SAVED_ANIMATION)
        }
    }
}

class SavedPhotoListAdapter(
    private val photos: List<Photo>,
    private val onItemClicked: ((Photo) -> Unit)? = null
) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(SAVED_PHOTO, binding)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position], onItemClicked)
    }
}

class PhotoViewHolder(
    private val type: Int,
    // Exposed for custom animator
    val binding: ListItemPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo?, onItemClicked: ((Photo) -> Unit)?, adapterCallback: (() -> Unit)? = null) {

        photo?.let {
            binding.image.load(it.uri)

            if (type == SYSTEM_PHOTO) {

                // TODO: Animations don't work

                Log.e("PhotoViewHolder", "APPLYING ROTATION")
                binding.image.rotation = if (it.isChosen) 45f else 0f

//                binding.image.animation?.let { animation ->
//                    animation.cancel()
//                }
//
//                if (binding.image.animation?.hasEnded() == false) {
//                } else {
//
//
//                }

            }
        }

        binding.image.setOnClickListener { _ ->
            photo?.let {
                // It is the code of SYSTEM_PHOTO, but it's more expensive to check it than to ignore it.
                photo.isChosen = true

                onItemClicked?.let { callback -> callback(it) }
            }
            adapterCallback?.let { it -> it() }
        }
    }
}