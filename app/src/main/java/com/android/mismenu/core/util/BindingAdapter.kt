package com.android.mismenu.core.util

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


@BindingAdapter("imageNetwork")
fun loadImageNetWork(imageView: ImageView, url: String){
    try{
        Glide.with(imageView).load(url).into(imageView)
    }catch (e: Exception){
        Log.e("EXCEPTION", "$e")
    }
}

@BindingAdapter("priceFormat")
fun priceFormat(textView: TextView, price: Int){
    val formatter = NumberFormat.getCurrencyInstance(Locale("VI", "vn")).format(price)
    textView.text = formatter
}
