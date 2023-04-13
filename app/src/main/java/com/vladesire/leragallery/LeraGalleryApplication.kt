package com.vladesire.leragallery

import android.app.Application
import com.vladesire.leragallery.photos.LocalPhotosService

class LeraGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SavedPhotosRepository.initialize(this)
        LocalPhotosService.initialize(this)
        PreferencesRepository.initialize(this)
    }
}