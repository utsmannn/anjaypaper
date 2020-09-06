package com.utsmannn.anjaypaper.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utsmannn.anjaypaper.R
import kotlinx.android.synthetic.main.item_loader.view.*

class PhotoStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PhotoStateAdapter.PhotoStateViewHolder>() {
    class PhotoStateViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: PhotoStateViewHolder, loadState: LoadState) {
        holder.itemView.run {
            progress_circular.isVisible = loadState !is LoadState.Error
            btn_retry.isVisible = loadState is LoadState.Error

            btn_retry.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): PhotoStateViewHolder {
        return PhotoStateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loader, parent, false))
    }
}