package com.android.mismenu.features.presentaion

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.mismenu.MainActivity
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

    protected fun popUpAlertDialog(activityContext: Context, title: String, message: String): MaterialAlertDialogBuilder{
        return MaterialAlertDialogBuilder(activityContext)
            .setTitle(title)
            .setMessage(message)
    }

}