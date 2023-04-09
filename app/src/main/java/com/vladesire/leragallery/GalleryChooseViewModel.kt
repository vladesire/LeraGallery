package com.vladesire.leragallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.vladesire.leragallery.photos.LocalPhotosService
import com.vladesire.leragallery.photos.Photo
import com.vladesire.leragallery.photos.PhotoPagingSource
import kotlinx.coroutines.flow.*

// Dependency injection
class GalleryChooseViewModel(
    private val photoRepository: PhotoRepository
): ViewModel() {
    private val _photos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val photos: StateFlow<List<Photo>> = _photos

    val photosFlow = Pager(
        PagingConfig(pageSize = 100, prefetchDistance = 25)
    ) {
        photoRepository.getLocalPhotosPagingSource()
    }.flow
        .cachedIn(viewModelScope)

    init {
//        viewModelScope.launch {
//
//            photoRepository.getPhotos(0, 20)
//                .combine(photoRepository.getSavedPhotos()) { photos, saved ->
//
//                    Log.i("GalleryChooseViewModel", "Saved size: ${saved.size}")
//
//                    photos.forEach { photo ->
//                        saved.forEach {
//                            if (photo.uri == it.uri) {
//                                photo.isChosen = true
//                            }
//                        }
//                    }
//                    photos
//                }.collect {
//                    Log.i("GalleryChooseViewModel", "Collected new photos")
//                    _photos.value = it
//                }
//        }

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