package com.vladesire.leragallery.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vladesire.leragallery.photos.Photo

@Database(entities = [ Photo::class ], version = 1)
@TypeConverters(PhotoTypeConverters::class)
abstract class PhotoDatabase: RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}