package android.example.com.socialmediaapp.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "friendship")
data class Friendship(

    @ColumnInfo(name = "user")
    @NonNull
    var user: String,

    @ColumnInfo(name = "friend")
    var friend: String,

    @ColumnInfo(name = "status")
    var status: FriendshipStatus
)