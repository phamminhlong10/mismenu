package com.android.mismenu.features.presentaion

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.mismenu.MainActivity

open class BaseFragment: Fragment() {
    @SuppressLint("RestrictedApi")
    protected fun showAppbar(isShow: Boolean){
        if(isShow){
            (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        }else{
            (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
            (activity as AppCompatActivity?)!!.supportActionBar!!.setShowHideAnimationEnabled(false)

        }
    }

    protected fun setTitleAppBar(title: String){
        (activity as MainActivity).supportActionBar?.title = title
    }

    protected fun turnOffShadowAppBar(){
        (activity as AppCompatActivity?)!!.supportActionBar!!.elevation = 0F
    }
}