package android.example.com.socialmediaapp.start.register

import android.app.Application
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlin.random.Random

class RegisterViewModel(
    private val database: SocialMediaDatabaseDao,
    application: Application
) : AndroidViewModel(application){

    private val BASE_URL = "https://picsum.photos/300?random="

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val context = getApplication<Application>().applicationContext

//    @Bindable
//    val inputUsername = MutableLiveData<String>()
//
//    @Bindable
//    val inputEmail = MutableLiveData<String>()
//
//    @Bindable
//    val inputPassword = MutableLiveData<String>()

    private val _registerIsSuccess = MutableLiveData<Boolean>()
    val registerIsSuccess: LiveData<Boolean> = _registerIsSuccess

    private val _errorInput = MutableLiveData<Boolean>()
    val errorInput: LiveData<Boolean> = _errorInput

    private val _accountIsExist = MutableLiveData<Boolean>()
    val accountIsExist: LiveData<Boolean> = _accountIsExist

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
        } else {
            uiScope.launch {
                try {
                    insertAccount(Account(username, email, password, Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)))
                    _registerIsSuccess.value = true
                } catch (e: Exception) {
                    Log.i("ERROR", e.toString())
                    _accountIsExist.value = true
                }
            }
        }
    }

    private suspend fun insertAccount(account: Account) {
        withContext(Dispatchers.IO) {
            val randomNumber = Random.nextInt(10000)
            val imageBitmap = Glide.with(context)
                .asBitmap()
                .load(BASE_URL + randomNumber)      // so the image is different when hit the same api
                .submit(300, 300)
                .get()
            account.imageProfile = imageBitmap
            database.insertAccount(account)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.i("RegisterViewModel", "RegisterViewModel destroyed!")
    }

}