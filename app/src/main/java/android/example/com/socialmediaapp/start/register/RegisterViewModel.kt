package android.example.com.socialmediaapp.start.register

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class RegisterViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application
) : AndroidViewModel(application), Observable {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    @Bindable
//    val inputUsername = MutableLiveData<String>()
//
//    @Bindable
//    val inputEmail = MutableLiveData<String>()
//
//    @Bindable
//    val inputPassword = MutableLiveData<String>()

    private val _registerIsSuccess = MutableLiveData<Boolean>()
    val registerIsSuccess : LiveData<Boolean> = _registerIsSuccess

    private val _errorInput = MutableLiveData<Boolean>()
    val errorInput : LiveData<Boolean> = _errorInput

    private val _accountIsExist = MutableLiveData<Boolean>()
    val accountIsExist : LiveData<Boolean> = _accountIsExist

    init {
        _registerIsSuccess.value = false
        _errorInput.value = false
        _accountIsExist.value = false
        Log.i("RegisterViewModel", "RegisterViewModel created!")
    }

    fun doneToastErrorInput() {
        _errorInput.value = false
    }

    fun doneToastAccountIsExist() {
        _accountIsExist.value = false
    }

    fun doneToastRegisterIsSuccess() {
        _registerIsSuccess.value = false
    }

    fun register(username: String, email: String, password: String) {
        if (username.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
            _errorInput.value = true
        }
        else {
            uiScope.launch {
                if (database.checkUsernameOrEmail(username, email)) {
                    _accountIsExist.value = true
                }
                else {
                    insertAccount(Account(username, email, password))
                    _registerIsSuccess.value = true
                }
            }
        }
    }

    private suspend fun insertAccount(account: Account) {
        withContext(Dispatchers.IO) {
            database.insertAccount(account)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("RegisterViewModel", "RegisterViewModel destroyed!")
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}