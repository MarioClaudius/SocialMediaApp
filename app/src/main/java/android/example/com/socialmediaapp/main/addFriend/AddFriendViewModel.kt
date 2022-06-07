package android.example.com.socialmediaapp.main.addFriend

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
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

    fun getAccountByUsername(username: String) {
        uiScope.launch {
            _friendAccount.value = database.getAccountByUsername(username)
        }
    }
}