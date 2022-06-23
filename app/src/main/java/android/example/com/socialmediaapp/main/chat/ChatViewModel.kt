package android.example.com.socialmediaapp.main.chat

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ChatViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
}