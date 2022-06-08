package android.example.com.socialmediaapp.database.entities

import androidx.room.TypeConverter

class FriendshipStatusConverter {

    @TypeConverter
    fun fromFriendshipStatus(status: FriendshipStatus) : String {
        return status.name
    }

    @TypeConverter
    fun toFriendshipStatus(status: String): FriendshipStatus {
        return FriendshipStatus.valueOf(status)
    }

}