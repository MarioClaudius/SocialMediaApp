package android.example.com.socialmediaapp.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatroom")
data class ChatRoom(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @NonNull
    var user1: String,

    @NonNull
    var user2: String
)