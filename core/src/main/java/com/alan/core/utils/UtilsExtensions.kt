package com.alan.core.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.alan.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri:String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.movie)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(Constants.IMAGES_BASE_URL + uri)
        .into(this)
}

fun ImageView.loadImage(uri:String?){
    val options = RequestOptions()
        .error(R.drawable.movie)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(Constants.IMAGES_BASE_URL + uri)
        .into(this)
}

fun View.initAnimation(animation: Animation?, onEnd: () -> Unit) {
    animation?.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit

        override fun onAnimationEnd(animation: Animation?) {
            onEnd()
        }

        override fun onAnimationRepeat(animation: Animation?) = Unit
    })
    this.startAnimation(animation)
}

