package com.utsmannn.anjaypaper.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.utsmannn.anjaypaper.R
import com.utsmannn.anjaypaper.ui.adapter.PhotoPagedAdapter
import com.utsmannn.anjaypaper.ui.adapter.PhotoStateAdapter
import com.utsmannn.anjaypaper.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.activity_bookmark.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarkActivity : AppCompatActivity() {

    private val photoViewModel: PhotoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        supportActionBar?.title = "Bookmark"

        val photoAdapter = PhotoPagedAdapter { photo ->
            val intent = Intent(this, PhotoDetailActivity::class.java).apply {
                putExtra("photo_id", photo.id)
                putExtra("photo_color", photo.color)
            }
            startActivity(intent)
        }

        val photoStateAdapter = PhotoStateAdapter {
            photoAdapter.retry()
        }

        photoStateAdapter.loadState = LoadState.Loading
        val layout = GridLayoutManager(this, 2).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (photoStateAdapter.loadState) {
                        is LoadState.NotLoading -> 1
                        is LoadState.Loading -> if (position == photoAdapter.itemCount) 2 else 1
                        is LoadState.Error -> if (position == photoAdapter.itemCount) 2 else 1
                        else -> 1
                    }
                }
            }
        }

        rv_photo.run {
            layoutManager = layout
            adapter = photoAdapter.withLoadStateFooter(photoStateAdapter)
        }

        lifecycleScope.launch {
            photoViewModel.bookmark.collect {
                photoAdapter.submitData(it)
                txt_empty.isVisible = photoAdapter.itemCount == 0
            }
        }
    }
}