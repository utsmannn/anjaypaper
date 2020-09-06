package com.utsmannn.anjaypaper.intent

import com.utsmannn.anjaypaper.model.Photo

sealed class BookmarkIntent {
    data class Bookmarking(val photo: Photo): BookmarkIntent()
    data class IsBookmark(val photo: Photo) : BookmarkIntent()
}