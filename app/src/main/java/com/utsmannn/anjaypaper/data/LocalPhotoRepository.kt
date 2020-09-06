package com.utsmannn.anjaypaper.data

import com.utsmannn.anjaypaper.utils.TemporaryListHelper
import com.utsmannn.anjaypaper.utils.logi
import com.utsmannn.anjaypaper.model.Photo

class LocalPhotoRepository {
    init {
        logi("----- localing -----")
    }

    fun photo(page: Int): List<Photo> = TemporaryListHelper.tempPhotos(page)
    fun insert(page: Int, photos: List<Photo>) = TemporaryListHelper.updateTemp(page, photos)

    fun bookmark() = TemporaryListHelper.bookmark()
    fun toggleBookmark(photo: Photo) = TemporaryListHelper.toggleBookmark(photo)
    fun bookmarked(photo: Photo) = TemporaryListHelper.isBookmarkedFlow(photo)
}