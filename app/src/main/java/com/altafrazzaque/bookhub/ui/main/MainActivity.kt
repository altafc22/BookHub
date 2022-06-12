package com.altafrazzaque.bookhub.ui.main

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.altafrazzaque.bookhub.R
import com.altafrazzaque.bookhub.base.BaseActivity
import com.altafrazzaque.bookhub.databinding.ActivityMainBinding
import com.altafrazzaque.bookhub.model.api.Books
import com.altafrazzaque.bookhub.model.api.VolumeInfo
import com.altafrazzaque.bookhub.ui.bookmark.BookmarkViewModel
import com.altafrazzaque.bookhub.ui.main.fragments.AboutFragment
import com.altafrazzaque.bookhub.ui.main.fragments.BookmarkFragment
import com.altafrazzaque.bookhub.ui.main.fragments.HomeFragment
import com.altafrazzaque.bookhub.ui.main.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val viewModel: MainViewModel by viewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition{ viewModel.isSplashLoading.value }
        }
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.bottomNav.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null)
        {
            supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        }
    }

    override fun setObservers() {

    }

    private lateinit var fragment: Fragment
    private var currentFragment = 0
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                fragment = HomeFragment()
                currentFragment = 0
            }
            R.id.nav_search -> {
                fragment = SearchFragment()
                currentFragment = 1
            }
            R.id.nav_info -> {
                fragment = AboutFragment()
                currentFragment = 2
            }
            R.id.nav_bookmarks -> {
                fragment = BookmarkFragment()
                currentFragment = 3
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        return true
    }

    fun replaceFragments(id:Int) {
        try {
            when (id) {
                R.id.nav_home -> {
                    fragment = HomeFragment()
                }
                R.id.nav_search -> {
                    fragment = SearchFragment()
                }
                R.id.nav_info -> {
                    fragment = AboutFragment()
                    currentFragment = 2
                }
                R.id.nav_bookmarks -> {
                    fragment = BookmarkFragment()
                    currentFragment = 3
                }
            }
            mainBinding.bottomNav.selectedItemId = id
        } catch (e: Exception) {
            e.printStackTrace()
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    override fun onBackPressed() {
        if(currentFragment!=0) {
            replaceFragments(R.id.nav_home)
            mainBinding.bottomNav.selectedItemId = R.id.nav_home
        }
        else
            super.onBackPressed()
    }

}