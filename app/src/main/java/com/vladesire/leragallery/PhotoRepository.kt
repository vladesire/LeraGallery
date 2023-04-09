package com.vladesire.leragallery

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.os.bundleOf
import androidx.room.Room
import com.vladesire.leragallery.database.PhotoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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


    fun getPhotos(page: Int, pageSize: Int = 100): Flow<List<Photo>> = flow {
        val photos = mutableListOf<Photo>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )

        val selection = bundleOf(
            ContentResolver.QUERY_ARG_LIMIT to pageSize,
            ContentResolver.QUERY_ARG_OFFSET to page * pageSize,
            ContentResolver.QUERY_ARG_SORT_COLUMNS to arrayOf(MediaStore.Files.FileColumns.DATE_ADDED),
            ContentResolver.QUERY_ARG_SORT_DIRECTION to ContentResolver.QUERY_SORT_DIRECTION_DESCENDING,
        )

        val query = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null
        )



        var counter = 0

        query?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {

                val id = cursor.getLong(idColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                photos += Photo(id = counter, uri = contentUri)
                counter += 1
            }
        }

        emit(photos)
    }


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