package com.android.mismenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigate = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)
        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottomNavigate, navController)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener{_, destination, _ ->
            when(destination.id){
                R.id.homeFragment -> bottomNavigate.visibility = View.VISIBLE
                else -> bottomNavigate.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}