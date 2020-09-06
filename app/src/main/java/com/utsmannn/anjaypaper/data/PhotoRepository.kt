package com.utsmannn.anjaypaper.data

import com.utsmannn.anjaypaper.model.Photo
import com.utsmannn.anjaypaper.network.NetworkInstance
import kotlinx.coroutines.delay

class PhotoRepository(private val instance: NetworkInstance) {
    suspend fun photo(page: Int): List<Photo> {
        delay(1000)
        return instance.photos(page)
    }

    suspend fun photo(id: String) = instance.photo(id)
}