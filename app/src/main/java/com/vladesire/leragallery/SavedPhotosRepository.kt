package com.vladesire.leragallery

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.vladesire.leragallery.database.PhotoDatabase
import com.vladesire.leragallery.photos.LocalPhotosService
import com.vladesire.leragallery.photos.Photo
import com.vladesire.leragallery.photos.PhotoPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow

private const val TAG = "PhotoRepository"

private const val DATABASE_NAME = "photos-database"
class SavedPhotosRepository private constructor(
    private val context: Context
){

    private val database: PhotoDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            PhotoDatabase::class.java,
            DATABASE_NAME
        ).build()

    suspend fun savePhoto(photo: Photo) {
        database.photoDao().savePhoto(photo)
    }

    suspend fun getSavedPhotos(): List<Photo> = database.photoDao().getPhotos()

    suspend fun eraseSavedPhotos(onErased: (() -> Unit)? = null) {
        database.photoDao().erase()
        onErased?.let { it() }
    }

    companion object {
        private var INSTANCE: SavedPhotosRepository? = null

        // Application's context lives long enough
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SavedPhotosRepository(context)
            }
        }

        fun get(): SavedPhotosRepository {
            return INSTANCE ?: throw IllegalStateException("SavedPhotosRepository must be initialized")
        }
    }
}