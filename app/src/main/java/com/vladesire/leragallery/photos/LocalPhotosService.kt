package com.vladesire.leragallery.photos

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.os.bundleOf
import com.vladesire.leragallery.SavedPhotosRepository

class LocalPhotosService private constructor(
    private val context: Context
) : PhotosService {
    override fun getPhotos(page: Int, pageSize: Int): List<Photo> {

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

        return photos
    }

    companion object {
        private var INSTANCE: LocalPhotosService? = null

        // Application's context lives long enough
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = LocalPhotosService(context)
            }
        }

        fun get(): LocalPhotosService {
            return INSTANCE ?: throw IllegalStateException("LocalPhotosService must be initialized")
        }
    }
}
