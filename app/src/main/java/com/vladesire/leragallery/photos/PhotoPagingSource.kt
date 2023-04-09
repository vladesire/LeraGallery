package com.vladesire.leragallery.photos

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

private const val TAG = "PhotoPagingSource"

class PhotoPagingSource(
    val backend: PhotosService
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {

        val page = params.key ?: 0

        val photos = backend.getPhotos(page, params.loadSize)

        val prevKey = if (page > 0) page - 1 else null
        val nextKey = if (photos.isNotEmpty()) page+1 else null

        Log.i(TAG, "Loading page: $page")

        return LoadResult.Page(photos, prevKey, nextKey)
    }

    // Currently no idea what it's doing TODO!!!!
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

}