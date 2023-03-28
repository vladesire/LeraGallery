package com.vladesire.leragallery

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

private const val TAG = "PhotoRepository"

class PhotoRepository private constructor(
    private val context: Context
){

    private val savedPhotos = mutableListOf<Photo>()

    fun savePhoto(photo: Photo) {
        savedPhotos += photo
        Log.i(TAG, "Saved photo $photo; (${savedPhotos.size})")
    }

    fun getSavedPhotos(): List<Photo> = savedPhotos

    fun getPhotos(number: Int): List<Photo> {

        val photos = mutableListOf<Photo>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED
        )

        val query = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "date_added DESC"
        )

        var counter = 0

        query?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)


            while (cursor.moveToNext()) {

                if (counter > number) break

                val id = cursor.getLong(idColumn)

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                photos += Photo(contentUri)
                counter += 1
            }
        }
        return photos
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