package com.ecommerce.calendly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BottomNavigation : AppCompatActivity() {
    private val homeFragment = HomeFragment();
    private val coinsFragment = CoinsFragment();
    private val moviesFragment = MoviesFragment();
    private lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        val bottomNavigationView : BottomNavigationView = findViewById(R.id.bottomNavigation)
        val toolbar :Toolbar = findViewById(R.id.toolbar)
        val drawerLayout: DrawerLayout =  findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        setSupportActionBar(toolbar)
        supportActionBar?.title ="Super app"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_hamburger)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opened,R.string.closed)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        goToFragment(homeFragment)
        bottomNavigationView.setOnItemSelectedListener {
            var fragment : Fragment = Fragment()
            when(it.itemId){
                R.id.menu_home ->  fragment =  homeFragment
                R.id.menu_coins-> fragment =  coinsFragment
                R.id.menu_movie-> fragment =  moviesFragment
            }
            goToFragment(fragment)
            return@setOnItemSelectedListener true
        }

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun goToFragment(fragment: Fragment){
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out,
                R.anim.fade_in,
                R.anim.fade_out
            )
            replace(R.id.fragement_container,fragment)
            setReorderingAllowed(true)
            addToBackStack("home") // name can be null
        }
    }


}