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
class PhotoRepository private constructor(
    private val context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){

    private val database: PhotoDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            PhotoDatabase::class.java,
            DATABASE_NAME
        ).build()

    private val savedPhotos = mutableListOf<Photo>()

    suspend fun savePhoto(photo: Photo) {
        database.photoDao().savePhoto(photo)
        Log.i(TAG, "Saved photo $photo; (${savedPhotos.size})")
    }

    fun getSavedPhotos(): Flow<List<Photo>> = database.photoDao().getPhotos()

    fun getLocalPhotosPagingSource() = PhotoPagingSource(LocalPhotosService(context))

    companion object {
        private var INSTANCE: PhotoRepository? = null

        // Application's context lives long enough
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PhotoRepository(context)
            }
        }

        fun get(): PhotoRepository {
            return INSTANCE ?: throw IllegalStateException("PhotoRepository must be initialized")
        }
    }
}