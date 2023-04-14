package com.vladesire.leragallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SwiperViewModel(
    private val savedPhotosRepository: SavedPhotosRepository
) : ViewModel() {

}


class SwiperViewModelFactory(
    private val savedPhotosRepository: SavedPhotosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SwiperViewModel(savedPhotosRepository) as T
    }
}