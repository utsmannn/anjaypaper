package com.utsmannn.anjaypaper.view_state

import com.utsmannn.anjaypaper.model.Photo

sealed class PhotoState {
    object Idle : PhotoState()
    object Loading : PhotoState()
    data class PhotoLoaded(val photo: Photo) : PhotoState()
    data class Error(val throwable: Throwable) : PhotoState()
}