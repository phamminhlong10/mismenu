package com.android.mismenu.core.util

class onClickListener<T>(val clickListener: (obj: T) -> Unit) {
    fun onClick(obj: T) = clickListener(obj)
}