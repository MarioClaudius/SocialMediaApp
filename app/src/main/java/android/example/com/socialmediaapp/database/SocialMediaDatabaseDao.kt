package android.example.com.socialmediaapp.database

import android.example.com.socialmediaapp.database.entities.Account
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SocialMediaDatabaseDao {
    @Insert
    fun insertAccount(account: Account)

    @Query("SELECT * FROM account WHERE (username = :usernameOrEmail OR email = :usernameOrEmail) AND password = :password")
    suspend fun getAccount(usernameOrEmail: String, password: String): Account

    @Query("SELECT * FROM account")
    fun getAllAccounts() : LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE username = 'test123456'")
    fun getFakeAccount() : LiveData<List<Account>>
}