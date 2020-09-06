package com.utsmannn.anjaypaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.utsmannn.anjaypaper.data.LocalPhotoRepository
import com.utsmannn.anjaypaper.data.PhotoDataSource
import com.utsmannn.anjaypaper.data.PhotoRepository
import com.utsmannn.anjaypaper.intent.BookmarkIntent
import com.utsmannn.anjaypaper.intent.PhotoIntent
import com.utsmannn.anjaypaper.model.Photo
import com.utsmannn.anjaypaper.view_state.BookmarkState
import com.utsmannn.anjaypaper.view_state.PhotoState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PhotoViewModel(
    private val repository: PhotoRepository,
    private val localPhotoRepository: LocalPhotoRepository
) : ViewModel() {
    private val photoState = MutableStateFlow<PhotoState>(PhotoState.Idle)
    private val bookmarkState = MutableStateFlow<BookmarkState>(BookmarkState.Idle)

    val photoIntent = Channel<PhotoIntent>(Channel.UNLIMITED)
    val bookmarkIntent = Channel<BookmarkIntent>(Channel.UNLIMITED)

    val state: StateFlow<PhotoState>
        get() = photoState

    val bState: StateFlow<BookmarkState>
        get() = bookmarkState

    val local = localPhotoRepository.photo(1)

    val data = Pager(PagingConfig(pageSize = 10)) {
        PhotoDataSource(repository, localPhotoRepository)
    }.flow.cachedIn(viewModelScope)

    val bookmark = localPhotoRepository.bookmark()
        .map { (it as MutableList<Photo>).run { PagingData.from(it) } }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            photoIntent.consumeAsFlow().collect {
                when (it) {
                    is PhotoIntent.GetPhoto -> getPhoto(it.id)
                }
            }
        }

        viewModelScope.launch {
            bookmarkIntent.consumeAsFlow().collect {
                when (it) {
                    is BookmarkIntent.Bookmarking -> bookmarking(it.photo)
                    is BookmarkIntent.IsBookmark -> checkBookmark(it.photo)
                }
            }
        }
    }

    private fun getPhoto(id: String) {
        viewModelScope.launch {
            photoState.run {
                value = PhotoState.Loading
                value = try {
                    PhotoState.PhotoLoaded(repository.photo(id))
                } catch (e: Throwable) {
                    PhotoState.Error(e)
                }
            }
        }
    }

    private fun bookmarking(photo: Photo) {
        viewModelScope.launch {
            bookmarkState.run {
                value = BookmarkState.Bookmarking(localPhotoRepository.toggleBookmark(photo))
            }
        }
    }

    private fun checkBookmark(photo: Photo) {
        viewModelScope.launch {
            bookmarkState.run {
                value = BookmarkState.Bookmarked(localPhotoRepository.bookmarked(photo))
            }
        }
    }
}