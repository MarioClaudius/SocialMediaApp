package android.example.com.socialmediaapp.main.chatroom

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Chat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ChatRoomViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application,
    roomId: Long,
    user: String
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val chatContentList : LiveData<List<Chat>> = database.getAllChatContentByRoomId(roomId)
}