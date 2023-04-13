package com.vladesire.leragallery

import android.content.Context
import com.vladesire.leragallery.photos.LocalPhotosService
import com.vladesire.leragallery.photos.PhotoPagingSource
import com.vladesire.leragallery.photos.PhotosService
import kotlinx.coroutines.CoroutineScope

class SystemPhotosRepository(
    private val photosService: PhotosService,
    private val coroutineScope: CoroutineScope
){

    fun getLocalPhotosPagingSource() = PhotoPagingSource(
        photosService,
        coroutineScope,
        SavedPhotosRepository.get()
    )

}