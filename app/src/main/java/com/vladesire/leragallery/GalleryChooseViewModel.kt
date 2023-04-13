package com.vladesire.leragallery

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.vladesire.leragallery.photos.Photo
import com.vladesire.leragallery.photos.PhotosService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Dependency injection
class GalleryChooseViewModel(
    private val savedPhotosRepository: SavedPhotosRepository,
    private val photosService: PhotosService
): ViewModel() {
    private val _photos: MutableStateFlow<PagingData<Photo>> = MutableStateFlow(PagingData.empty())
    val photos: StateFlow<PagingData<Photo>> = _photos

    private val photosFlow = Pager(
        PagingConfig(pageSize = 100, prefetchDistance = 25)
    ) {
        SystemPhotosRepository(photosService, viewModelScope).getLocalPhotosPagingSource()
    }.flow
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            photosFlow.collect {
                _photos.value = it
            }
        }
    }

    suspend fun savePhoto(photo: Photo) {
        savedPhotosRepository.savePhoto(photo)
    }
}

class GalleryChooseViewModelFactory(
    private val savedPhotosRepository: SavedPhotosRepository,
    private val photosService: PhotosService
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GalleryChooseViewModel(savedPhotosRepository, photosService) as T
    }
}