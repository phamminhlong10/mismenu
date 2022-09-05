package com.android.mismenu.core.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageNetwork")
fun loadImageNetWork(imageView: ImageView, url: String){
    try{
        Glide.with(imageView).load(url).into(imageView)
    }catch (e: Exception){
        Log.e("EXCEPTION", "$e")
    }
}