package android.example.com.socialmediaapp.main.chatroom

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChatRoomViewModelFactory(
    private val application: Application,
    private val dataSource: SocialMediaDatabaseDao,
    private val roomId: Long,
    private val user: String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatRoomViewModel::class.java)) {
            return ChatRoomViewModel(dataSource, application, roomId, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}