package com.vladesire.leragallery.photos

import androidx.recyclerview.widget.DiffUtil

object PhotoComparator : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.uri == newItem.uri
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.uri == newItem.uri && oldItem.isChosen == newItem.isChosen
    }

}