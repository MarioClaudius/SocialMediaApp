package android.example.com.socialmediaapp.main.home

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.database.entities.Friendship
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application,
    username: String
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val friendList : LiveData<List<Account>> = database.getAllFriends(username)

    val friendRequestList : LiveData<List<Friendship>> = database.getAllFriendRequests(username)

    private val _friendListDropDown = MutableLiveData<Boolean>()
    val friendListDropDown : LiveData<Boolean> = _friendListDropDown

    private val _friendRequestListDropDown = MutableLiveData<Boolean>()
    val friendRequestListDropDown : LiveData<Boolean> = _friendRequestListDropDown

    init {
        _friendListDropDown.value = true
        _friendRequestListDropDown.value = true
    }

    fun showOrHideFriendList() {
        _friendListDropDown.value = !(_friendListDropDown.value)!!
    }

    fun showOrHideFriendRequestList() {
        _friendRequestListDropDown.value = !(_friendRequestListDropDown.value)!!
    }
}