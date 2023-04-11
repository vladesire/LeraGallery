package com.vladesire.leragallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.vladesire.leragallery.photos.LocalPhotosService
import com.vladesire.leragallery.photos.Photo
import com.vladesire.leragallery.photos.PhotoPagingSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Dependency injection
class GalleryChooseViewModel(
    private val photoRepository: PhotoRepository
): ViewModel() {
    private val _photos: MutableStateFlow<PagingData<Photo>> = MutableStateFlow(PagingData.empty())
    val photos: StateFlow<PagingData<Photo>> = _photos

    private val savedPhotosFlow = photoRepository.getSavedPhotos()

    private val photosFlow = Pager(
        PagingConfig(pageSize = 100, prefetchDistance = 25)
    ) {
        photoRepository.getLocalPhotosPagingSource()
    }.flow
        .combine(savedPhotosFlow) { photos, saved ->
            photos.map { photo ->
                saved.forEach {
                    if (it.uri == photo.uri) {
                        photo.isChosen = true
                        return@forEach
                    }
                }
                photo
            }
        }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            photosFlow.collect {
                _photos.value = it
            }
        }
    }

    suspend fun savePhoto(photo: Photo) {
        photoRepository.savePhoto(photo)
    }
}

class GalleryChooseViewModelFactory(
    private val photoRepository: PhotoRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryChooseViewModel(photoRepository) as T
    }
}