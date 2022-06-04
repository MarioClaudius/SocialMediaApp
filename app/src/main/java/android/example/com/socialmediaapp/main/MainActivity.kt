package android.example.com.socialmediaapp.main

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

//    private lateinit var viewModelJob: Job
//
//    private lateinit var uiScope: CoroutineScope
//
//    private lateinit var dataSource: SocialMediaDatabaseDao

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_main_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.chatFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigationView.setupWithNavController(navController)

//        viewModelJob = Job()
//
//        uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//
//        dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao
//
//        uiScope.launch {
//            val data = getAllAccounts()
//
//            val textview = findViewById<TextView>(R.id.TEXT_MAIN_ACTIVITY)
//
//            var str = ""
//            for(account in data) {
//                str += account.username
//            }
//
//            textview.text = str
//        }
//
    }
//
//    suspend fun getAllAccounts() : List<Account>{
//        val list: List<Account>
//        withContext(Dispatchers.IO) {
//            list = dataSource.getAllAccounts()
//        }
//        return list
//    }
}