package android.example.com.socialmediaapp.database

import android.example.com.socialmediaapp.database.entities.*
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SocialMediaDatabaseDao {
    @Insert
    fun insertAccount(account: Account)

    @Query("SELECT * FROM account WHERE (username = :usernameOrEmail OR email = :usernameOrEmail) AND password = :password")
    suspend fun getAccount(usernameOrEmail: String, password: String): Account

    @Query("SELECT * FROM account")
    fun getAllAccounts(): LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE username = 'test123456'")
    fun getFakeAccount(): LiveData<List<Account>>

    @Query("SELECT * FROM account WHERE username = :username")
    suspend fun getAccountByUsername(username: String): Account

    @Insert
    suspend fun insertFriendship(friendship: Friendship)

    @Query("SELECT * FROM friendship WHERE friend = :username AND status = 'PENDING'")
    fun getAllFriendRequests(username: String): LiveData<List<Friendship>>

    @Query("SELECT * FROM account LEFT JOIN friendship ON account.username = friendship.friend WHERE friendship.user = :username AND status = 'ACTIVE'")
    fun getAllFriends(username: String): LiveData<List<Account>>

    @Update
    suspend fun updateFriendRequests(friendship: Friendship)

    @Query("SELECT * FROM friendship WHERE user = :user AND friend = :friend AND status = 'PENDING'")
    suspend fun getPendingFriendRequestByUserAndFriend(user: String, friend: String) : Friendship

    @Insert
    suspend fun insertActiveFriendRequest(friendship: Friendship)

    @Insert
    suspend fun insertRejectedFriendRequest(friendship: Friendship)

    @Query("SELECT * FROM friendship")
    suspend fun getAllFriendship() : List<Friendship>

    @Query("DELETE FROM friendship")
    suspend fun clearFriendship()

    @Query("SELECT status FROM friendship where user = :user AND friend = :friend ORDER BY id DESC LIMIT 1")
    suspend fun checkFriendRequest(user: String, friend: String) : FriendshipStatus

    @Query("SELECT * FROM chat INNER JOIN chatroom ON chat.room_id = chatroom.id WHERE room_id = :roomId ORDER BY timestamp")
    fun getAllChatContentByRoomId(roomId : Long) : LiveData<List<Chat>>

    @Query("SELECT * FROM chatroom WHERE (user1 = :user1 AND user2 = :user2) OR (user1 = :user2 AND user2 = :user1) LIMIT 1")
    suspend fun getChatRoomByUserAndFriend(user1: String, user2: String) : ChatRoom

    @Query("SELECT * FROM chatroom WHERE user1 = :user OR user2 = :user")
    fun getChatRoomByUser(user: String) : LiveData<List<ChatRoom>>

    @Query("SELECT * FROM chat WHERE room_id = :roomId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastChatByChatRoom(roomId: Long) : Chat

    @Insert
    suspend fun insertChatRoom(chatRoom: ChatRoom)

    @Insert
    suspend fun insertChat(chat: Chat)
}