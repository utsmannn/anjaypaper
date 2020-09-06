package com.utsmannn.anjaypaper.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.signature.ObjectKey
import com.utsmannn.anjaypaper.GlideApp

fun ImageView.loadUrl(url: String, id: String, requestListener: RequestListener<Drawable>? = null, placeholder: Drawable? = null) = run {
    val sign = ObjectKey(id)
    GlideApp.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .addListener(requestListener)
        .placeholder(placeholder)
        .signature(sign)
        .into(this)
        .clearOnDetach()
}

fun ImageView.loadWithThumbnail(url: String, thumbnail: String, id: String, requestListener: RequestListener<Drawable>? = null, placeholder: Drawable? = null) = run {
    val sign = ObjectKey(id)
    GlideApp.with(context)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .addListener(requestListener)
        .placeholder(placeholder)
        .thumbnail(GlideApp.with(context).load(thumbnail))
        .signature(sign)
        .into(this)
        .clearOnDetach()
}