package com.vladesire.leragallery.database

import android.net.Uri
import androidx.room.TypeConverter

class PhotoTypeConverters {

    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(stringUri: String): Uri {
        return Uri.parse(stringUri)
    }

}