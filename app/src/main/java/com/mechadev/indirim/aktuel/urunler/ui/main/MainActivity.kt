package com.mechadev.indirim.aktuel.urunler.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mechadev.indirim.aktuel.urunler.R
import com.mechadev.indirim.aktuel.urunler.util.extensions.changeTextWithAnimation
import com.mechadev.indirim.aktuel.urunler.databinding.ActivityMainBinding
import com.mechadev.toaster.Toaster
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragment_home -> navController.navigate(R.id.homeFragment)
                R.id.fragment_shopping_list -> navController.navigate(R.id.shoppingListFragment)
            }
            true
        }


    }

    override fun onStart() {
        super.onStart()
        binding.headerText.changeTextWithAnimation(getString(R.string.toolbar_recently_added))
    }

    override fun onStop() {
        super.onStop()
        binding.headerText.text = getString(R.string.app_name)
    }
}