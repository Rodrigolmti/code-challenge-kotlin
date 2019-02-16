package com.arctouch.codechallenge.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.util.HOME_FRAGMENT_LABEL

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                HOME_FRAGMENT_LABEL -> {
                    controlBackButtonVisibility(false)
                }
                else -> {
                    controlBackButtonVisibility(true)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                navController.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun controlBackButtonVisibility(visible: Boolean) {
        supportActionBar?.let {
            it.apply {
                setDisplayHomeAsUpEnabled(visible)
                setHomeButtonEnabled(visible)
            }
        }
    }
}
