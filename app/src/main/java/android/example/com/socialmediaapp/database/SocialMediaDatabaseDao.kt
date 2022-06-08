package android.example.com.socialmediaapp.database

import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.database.entities.Friendship
import android.example.com.socialmediaapp.database.entities.FriendshipStatus
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

    @Query("SELECT * FROM account WHERE username = :username")
    suspend fun getAccountByUsername(username: String) : Account

    @Insert
    suspend fun insertFriendship(friendship: Friendship)

    @Query("SELECT * FROM friendship WHERE friend = :username AND status = 'PENDING'")
    fun getAllFriendRequests(username: String) : LiveData<List<Friendship>>

}