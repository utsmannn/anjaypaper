package com.utsmannn.anjaypaper.data

import androidx.paging.PagingSource
import com.utsmannn.anjaypaper.model.Photo

class PhotoDataSource(
    private val repository: PhotoRepository,
    private val localPhotoRepository: LocalPhotoRepository
) : PagingSource<Int, Photo>() {
    private var page: Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: page
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = currentPage + 1
            val response = repository.photo(currentPage)
            if (currentPage == 1) {
                localPhotoRepository.insert(1, response)
            }
            LoadResult.Page(response, prevPage, nextPage)
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}