package com.utsmannn.anjaypaper.view_state

import kotlinx.coroutines.flow.Flow

sealed class BookmarkState {
    object Idle : BookmarkState()
    data class Bookmarked(val isBook: Flow<Boolean>) : BookmarkState()
    data class Bookmarking(val action: Unit) : BookmarkState()
}