package android.example.com.socialmediaapp.database

import android.content.Context
import android.example.com.socialmediaapp.database.entities.Account
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Account::class], version = 1, exportSchema = false)
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