package com.nxe.managethings.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nxe.managethings.data.model.CategoryInfo
import com.nxe.managethings.data.model.NoOfTaskForEachCategory
import com.nxe.managethings.data.model.TaskCategoryInfo
import com.nxe.managethings.data.model.TaskInfo
import com.nxe.managethings.domain.TaskCategoryRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nxe.managethings.R
import com.nxe.managethings.data.db.NoteDatabase
import com.nxe.managethings.data.repository.NoteRepository
import com.nxe.managethings.data.viewModel.NoteActivityViewModel
import com.nxe.managethings.data.viewModel.NoteActivityViewModelFactory
import com.nxe.managethings.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var noteActivityViewModel: NoteActivityViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    val viewModel : MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.appBarMain.toolbar)
        try {
            setSupportActionBar(binding.appBarMain.toolbar)
            val navController = findNavController(R.id.nav_host_fragment_content_main)
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.home_fragment, R.id.settings_fragment, R.id.completed_tasks_fragment
                ), binding.drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)

            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            setupWithNavController(bottomNavigationView,navController)

            binding.navView.setupWithNavController(navController)



            val noteRepository = NoteRepository(NoteDatabase(this))
            val noteActivityViewModelFactory = NoteActivityViewModelFactory(noteRepository)
            noteActivityViewModel = ViewModelProvider(this, noteActivityViewModelFactory)[NoteActivityViewModel::class.java]
        }
        catch (e: Exception)
        {

        }


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}