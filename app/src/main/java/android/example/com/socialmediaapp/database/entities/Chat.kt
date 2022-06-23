package android.example.com.socialmediaapp.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "room_id")
    @NonNull
    var roomId: Long,

    @ColumnInfo(name = "chat")
    @NonNull
    var content: String,

    @ColumnInfo(name = "timestamp")
    @NonNull
    var timestamp: Long,

    @ColumnInfo(name = "user_id")
    @NonNull
    var userId: String
)
