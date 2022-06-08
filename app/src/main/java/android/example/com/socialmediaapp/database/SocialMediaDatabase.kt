package android.example.com.socialmediaapp.database

import android.content.Context
import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.database.entities.Friendship
import android.example.com.socialmediaapp.database.entities.FriendshipStatusConverter
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Account::class, Friendship::class], version = 2, exportSchema = false)
@TypeConverters(FriendshipStatusConverter::class)
abstract class SocialMediaDatabase: RoomDatabase() {
    abstract val socialMediaDatabaseDao: SocialMediaDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: SocialMediaDatabase? = null

        fun getInstance(context: Context) : SocialMediaDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SocialMediaDatabase::class.java,
                        "social_media_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}