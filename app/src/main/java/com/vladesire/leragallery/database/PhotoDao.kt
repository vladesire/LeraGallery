package com.vladesire.leragallery.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vladesire.leragallery.photos.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    suspend fun getPhotos(): List<Photo>

    @Insert
    suspend fun savePhoto(photo: Photo)

    @Query("DELETE FROM photos")
    suspend fun erase()
}