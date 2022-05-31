package android.example.com.socialmediaapp.database.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "account", indices = [Index(value = ["username", "email"], unique = true)])
data class Account(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    @NonNull
    var username: String,

    @ColumnInfo(name = "email")
    @NonNull
    var email: String,

    @ColumnInfo(name = "password")
    @NonNull
    var password: String
)
