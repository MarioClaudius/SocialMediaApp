package android.example.com.socialmediaapp.main.addFriend

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.database.entities.Friendship
import android.example.com.socialmediaapp.database.entities.FriendshipStatus
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class AddFriendViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _friendAccount = MutableLiveData<Account>()
    val friendAccount : LiveData<Account> = _friendAccount

    private val _friendRequestStatus = MutableLiveData<FriendshipStatus>()
    val friendRequestStatus : LiveData<FriendshipStatus> = _friendRequestStatus

    fun getAccountByUsername(username: String) {
        uiScope.launch {
            _friendAccount.value = database.getAccountByUsername(username)
        }
    }

    fun checkAccountFriendRequest(user: String, friend: String) {
        uiScope.launch {
            _friendRequestStatus.value = database.checkFriendRequest(user, friend)
//            _friendRequestStatus.value = friendship.status
        }
    }

    fun insertFriendship(friendship: Friendship) {
        uiScope.launch {
            database.insertFriendship(friendship)
        }
    }
}