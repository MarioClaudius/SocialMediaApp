package android.example.com.socialmediaapp.database

import android.example.com.socialmediaapp.database.entities.Account
import androidx.room.Dao
import androidx.room.Insert

@Dao
interface SocialMediaDatabaseDao {
    @Insert
    fun insertAccount(account: Account)
}