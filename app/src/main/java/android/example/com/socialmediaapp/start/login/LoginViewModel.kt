package android.example.com.socialmediaapp.start.login

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*

class LoginViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _errorInput = MutableLiveData<Boolean>()
    val errorInput : LiveData<Boolean> = _errorInput

    private val _accountIsNotExist = MutableLiveData<Boolean>()
    val accountIsNotExist : LiveData<Boolean> = _accountIsNotExist

    private val _loginIsSuccess = MutableLiveData<Boolean>()
    val loginIsSuccess : LiveData<Boolean> = _loginIsSuccess

    init {
        _errorInput.value = false
        _accountIsNotExist.value = false
        _loginIsSuccess.value = false
    }

    fun doneToastErrorInput() {
        _errorInput.value = false
    }

    fun doneToastAccountIsNotExist() {
        _accountIsNotExist.value = false
    }

    fun doneToastLoginIsSuccess() {
        _loginIsSuccess.value = false
    }

    fun login(usernameOrEmail: String, password: String) {
        if (usernameOrEmail.isNullOrEmpty() || password.isNullOrEmpty()) {
            _errorInput.value = true
        }
        else {
            uiScope.launch {
                val account = database.getAccount(usernameOrEmail, password)
                if (account == null) {
                    _accountIsNotExist.value = true
                }
                else {
                    _loginIsSuccess.value = true
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}