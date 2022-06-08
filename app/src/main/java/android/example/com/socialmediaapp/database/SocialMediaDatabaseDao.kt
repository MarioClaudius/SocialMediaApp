package android.example.com.socialmediaapp.database

import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.database.entities.Friendship
import android.example.com.socialmediaapp.database.entities.FriendshipStatus
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SocialMediaDatabaseDao {
    @Insert
    fun insertAccount(account: Account)

    @Query("SELECT * FROM account WHERE (username = :usernameOrEmail OR email = :usernameOrEmail) AND password = :password")
    suspend fun getAccount(usernameOrEmail: String, password: String): Account

    @Query("SELECT * FROM account")
    fun getAllAccounts(): LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE username = 'test123456'")
    fun getFakeAccount(): LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE username = :username")
    suspend fun getAccountByUsername(username: String): Account

    @Insert
    suspend fun insertFriendship(friendship: Friendship)

    @Query("SELECT * FROM friendship WHERE friend = :username AND status = 'PENDING'")
    fun getAllFriendRequests(username: String): LiveData<List<Friendship>>

    @Query("SELECT * FROM account LEFT JOIN friendship ON account.username = friendship.friend WHERE friendship.user = :username AND status = 'ACTIVE'")
    fun getAllFriends(username: String): LiveData<List<Account>>

    @Update
    suspend fun updateFriendRequests(friendship: Friendship)

    @Query("SELECT * FROM friendship WHERE user = :user AND friend = :friend AND status = 'PENDING'")
    suspend fun getPendingFriendRequestByUserAndFriend(user: String, friend: String) : Friendship

    @Insert
    suspend fun insertActiveFriendRequest(friendship: Friendship)

    @Insert
    suspend fun insertRejectedFriendRequest(friendship: Friendship)

    @Query("SELECT * FROM friendship")
    suspend fun getAllFriendship() : List<Friendship>

    @Query("DELETE FROM friendship")
    suspend fun clearFriendship()

    @Query("SELECT status FROM friendship where user = :user AND friend = :friend ORDER BY id DESC LIMIT 1")
    suspend fun checkFriendRequest(user: String, friend: String) : FriendshipStatus
}