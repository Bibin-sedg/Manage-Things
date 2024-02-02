package com.nxe.managethings.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nxe.managethings.R
import com.nxe.managethings.databinding.ActivityMainBinding
import com.nxe.managethings.db.NoteDatabase
import com.nxe.managethings.repository.NoteRepository
import com.nxe.managethings.viewModel.NoteActivityViewModel
import com.nxe.managethings.viewModel.NoteActivityViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var noteActivityViewModel: NoteActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    val viewModel : MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        try {
            setContentView(binding.root)
            val noteRepository = NoteRepository(NoteDatabase(this))
            val noteActivityViewModelFactory = NoteActivityViewModelFactory(noteRepository)
            noteActivityViewModel = ViewModelProvider(this, noteActivityViewModelFactory)[NoteActivityViewModel::class.java]

            val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment
            navController = navHostFragment.navController
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottmNavigationView)
            setupWithNavController(bottomNavigationView,navController)
        }
        catch (e: Exception){
            Log.d("TAG","Error")
        }


    }
}