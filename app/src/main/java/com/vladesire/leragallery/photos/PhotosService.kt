package com.vladesire.leragallery.photos

fun interface PhotosService {
    fun getPhotos(page: Int, pageSize: Int): List<Photo>
}
