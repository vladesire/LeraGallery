package com.vladesire.leragallery.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vladesire.leragallery.photos.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    fun getPhotos(): Flow<List<Photo>>

    @Insert
    suspend fun savePhoto(photo: Photo)

}