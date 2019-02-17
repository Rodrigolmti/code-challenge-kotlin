package com.arctouch.codechallenge.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.util.HOME_FRAGMENT_LABEL

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var menu: Menu? = null

    val queryString = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label) {
                HOME_FRAGMENT_LABEL -> {
                    controlBackButtonVisibility(false)
                    controlMenuVisibility(true)
                }
                else -> {
                    controlBackButtonVisibility(true)
                    controlMenuVisibility(false)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        menu?.findItem(R.id.action_search)?.let { setupSearchView(it) }
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {

            }
            android.R.id.home -> {
                navController.popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupSearchView(myActionMenuItem: MenuItem) {
        val searchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                queryString.value = query
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                queryString.value = query
                return true
            }
        })
    }

    private fun controlBackButtonVisibility(visible: Boolean) {
        supportActionBar?.let {
            it.apply {
                setDisplayHomeAsUpEnabled(visible)
                setHomeButtonEnabled(visible)
            }
        }
    }

    private fun controlMenuVisibility(visible: Boolean) {
        try {

            menu?.let {
                for (i in 0..(it.size() - 1)) {
                    it.getItem(i).isVisible = visible
                }
            }

        } catch (error: Exception) {
            error.printStackTrace()
        }
    }
}
