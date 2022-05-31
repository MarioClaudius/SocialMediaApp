package android.example.com.socialmediaapp.database

import android.example.com.socialmediaapp.database.entities.Account
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SocialMediaDatabaseDao {
    @Insert
    fun insertAccount(account: Account)

    @Query("SELECT CASE WHEN EXISTS ( SELECT * FROM account WHERE username = :username OR email = :email) THEN 'TRUE' ELSE 'FALSE' END")
    fun checkUsernameOrEmail(username: String, email: String): Boolean
}