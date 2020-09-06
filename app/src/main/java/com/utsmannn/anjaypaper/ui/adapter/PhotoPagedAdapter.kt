package com.utsmannn.anjaypaper.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.utsmannn.anjaypaper.R
import com.utsmannn.anjaypaper.utils.loadUrl
import com.utsmannn.anjaypaper.utils.logi
import com.utsmannn.anjaypaper.model.Photo
import kotlinx.android.synthetic.main.item_list.view.*

class PhotoPagedAdapter(private val click: (Photo) -> Unit) : PagingDataAdapter<Photo, PhotoPagedAdapter.PhotoViewHolder>(PhotoDiffUtil) {

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("Range")
        fun bind(photo: Photo, click: Photo.() -> Unit) = itemView.run {
            logi("item loaded -> ${photo.id}")
            val colorDrawable = ColorDrawable(Color.parseColor(photo.color))
            img_photo.loadUrl(url = photo.urls.small, id = photo.id, placeholder = colorDrawable)

            setOnClickListener {
                click.invoke(photo)
            }
        }
    }

    object PhotoDiffUtil : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) holder.bind(item, click)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }
}