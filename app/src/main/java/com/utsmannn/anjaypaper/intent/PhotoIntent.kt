package com.utsmannn.anjaypaper.intent

sealed class PhotoIntent {
    data class GetPhoto(val id: String) : PhotoIntent()
}