package com.android.mismenu.core.util

import java.util.*
import javax.inject.Inject

class Utils @Inject constructor(){
    fun welcomeText(): String{
        return when(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 5..11 -> "Good morning"
            in 12..17 -> "Good afternoon"
            else -> "Good evening"
        }
    }
}