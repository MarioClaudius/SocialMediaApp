package android.example.com.socialmediaapp.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friendship")
data class Friendship(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "user")
    @NonNull
    var user: String,

    @ColumnInfo(name = "friend")
    @NonNull
    var friend: String,

    @ColumnInfo(name = "status")
    @NonNull
    var status: FriendshipStatus
)