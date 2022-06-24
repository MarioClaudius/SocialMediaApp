package android.example.com.socialmediaapp.main.chat

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatViewModelFactory(
    private val application: Application,
    private val dataSource: SocialMediaDatabaseDao,
    private val user: String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(dataSource, application, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}