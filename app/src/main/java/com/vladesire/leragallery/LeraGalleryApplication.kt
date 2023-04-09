package com.vladesire.leragallery

import android.app.Application

class LeraGalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PhotoRepository.initialize(this)
        PreferencesRepository.initialize(this)
    }
}