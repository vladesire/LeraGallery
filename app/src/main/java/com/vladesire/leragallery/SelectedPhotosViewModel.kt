package com.vladesire.leragallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SelectedPhotosViewModel(
    private val photosRepository: PhotoRepository
): ViewModel() {

    private val _savedPhotos: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val savedPhotos: StateFlow<List<Photo>>
        get() = _savedPhotos.asStateFlow()

    init {
        viewModelScope.launch {
            photosRepository.getSavedPhotos().collect {
                _savedPhotos.value = it
            }
        }
    }

}

class SelectedPhotosViewModelFactory(
    private val photosRepository: PhotoRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectedPhotosViewModel(photosRepository) as T
    }
}