package com.vladesire.leragallery

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uri: Uri
) {
    // Room complains about it being in the primary constructor
    @Ignore var isChosen: Boolean = false
}