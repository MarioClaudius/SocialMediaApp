package android.example.com.socialmediaapp.start

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.databinding.ActivityStartBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        val navController = this.findNavController(R.id.nav_start_fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_start_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}