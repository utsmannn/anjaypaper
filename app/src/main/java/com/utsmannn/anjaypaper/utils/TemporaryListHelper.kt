package com.utsmannn.anjaypaper.utils

import com.utsmannn.anjaypaper.model.Photo
import com.utsmannn.pocketdb.Pocket
import com.utsmannn.pocketdb.extensions.defaultCollectionOf
import com.utsmannn.pocketdb.extensions.defaultOf
import com.utsmannn.pocketdb.extensions.listen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TemporaryListHelper {

    private val listCollection = Pocket.collection("cache")
    private val listDefault = defaultCollectionOf(emptyList<Photo>())

    private val bookmarkCollection = Pocket.collection("collection_bookmark")

    fun tempPhotos(page: Int) = run {
        listCollection.selectOf("$page", listDefault).toList()
    }

    fun updateTemp(page: Int, list: List<Photo>) = run {
        logi("updating temp...")
        logi("destroy temp...")
        listCollection.destroy()
        logi("insert temp...")
        listCollection.insertAll("$page", list)
    }

    fun toggleBookmark(photo: Photo) {
        val isExist = isBookmarked(photo)
        if (isExist) {
            removeBookmark(photo)
        } else {
            insertBookmark(photo)
        }
    }

    private fun insertBookmark(photo: Photo) {
        bookmarkCollection.insert("bookmark", photo)
    }

    private fun removeBookmark(photo: Photo) {
        val editedBookmark = bookmarkCollection.selectOf("bookmark", listDefault)
            .toMutableList().apply {
                remove(photo)
            }

        bookmarkCollection.destroy("bookmark")
        bookmarkCollection.insertAll("bookmark", editedBookmark)
    }

    private fun isBookmarked(photo: Photo): Boolean = run {
        val photoFound = bookmarkCollection.selectOf("bookmark", listDefault)
            .find { it.id == photo.id }
        photoFound != null
    }

    fun isBookmarkedFlow(photo: Photo): Flow<Boolean> = run {
        bookmarkCollection.flowOf("bookmark", listDefault).map { it.contains(photo) }
    }

    fun bookmark() = run {
        bookmarkCollection.flowOf("bookmark", listDefault).map { it.toMutableList() }
    }
}