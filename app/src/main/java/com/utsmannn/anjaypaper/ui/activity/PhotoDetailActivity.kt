package com.utsmannn.anjaypaper.ui.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.utsmannn.anjaypaper.R
import com.utsmannn.anjaypaper.intent.BookmarkIntent
import com.utsmannn.anjaypaper.intent.PhotoIntent
import com.utsmannn.anjaypaper.model.Photo
import com.utsmannn.anjaypaper.utils.loadWithThumbnail
import com.utsmannn.anjaypaper.utils.logi
import com.utsmannn.anjaypaper.utils.makeStatusBarTransparent
import com.utsmannn.anjaypaper.view_state.BookmarkState
import com.utsmannn.anjaypaper.view_state.PhotoState
import com.utsmannn.anjaypaper.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.activity_photo_detail.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoDetailActivity : AppCompatActivity() {

    private val photoViewModel: PhotoViewModel by viewModel()
    private val photoId by lazy { intent.getStringExtra("photo_id") }
    private val photoColor by lazy { intent.getStringExtra("photo_color") }

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        makeStatusBarTransparent()

        lifecycleScope.launch {
            photoViewModel.photoIntent.send(PhotoIntent.GetPhoto(photoId))
        }

        img_photo.setBackgroundColor(Color.parseColor(photoColor))

        lifecycleScope.launch {
            photoViewModel.state.collect {
                when (it) {
                    is PhotoState.PhotoLoaded -> {
                        setupViewLoaded(it.photo)
                    }
                    is PhotoState.Loading -> {
                        logi("loading.....")
                    }
                    is PhotoState.Idle -> {
                        logi("idle....")
                    }
                    is PhotoState.Error -> {
                        logi("error -> ${it.throwable.message}")
                    }
                }
            }
        }

        lifecycleScope.launch {
            photoViewModel.bState.collect { state ->
                when (state) {
                    is BookmarkState.Bookmarked -> {
                        state.isBook.collect {
                            val resId = if (it) R.drawable.ic_baseline_bookmark_24 else R.drawable.ic_baseline_bookmark_border_24
                            img_bookmark.setImageResource(resId)
                        }
                    }
                    is BookmarkState.Bookmarking -> {
                        logi("bookmarking...")
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewLoaded(photo: Photo) {
        lifecycleScope.launch {
            photoViewModel.bookmarkIntent.send(BookmarkIntent.IsBookmark(photo))
        }

        val colorPlaceholder = ColorDrawable(Color.parseColor(photoColor))
        img_photo.loadWithThumbnail(
            photo.urls.regular,
            photo.urls.small,
            photo.id,
            placeholder = colorPlaceholder
        )

        txt_author.text = "Taken by ${photo.user.name}\nfrom http://unsplash.com"

        img_bookmark.setOnClickListener {
            lifecycleScope.launch {
                photoViewModel.bookmarkIntent.send(BookmarkIntent.Bookmarking(photo))
            }
        }
    }
}