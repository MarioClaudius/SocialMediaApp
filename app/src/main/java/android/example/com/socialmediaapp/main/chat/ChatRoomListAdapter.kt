package android.example.com.socialmediaapp.main.chat

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.ChatRoom
import android.example.com.socialmediaapp.main.MainActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatRoomListAdapter(
    private val database: SocialMediaDatabaseDao,
    private val user: String
) : RecyclerView.Adapter<ChatRoomListAdapter.ViewHolder>() {

    var data = listOf<ChatRoom>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_chat_room, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        val chatroom = data.get(position)
        if (chatroom.user1.equals(user)) {
            holder.nameTv.text = chatroom.user2
            uiScope.launch {
                val user2 = database.getAccountByUsername(chatroom.user2)
                holder.photo.setImageBitmap(user2.imageProfile)
            }
        } else {
            holder.nameTv.text = chatroom.user1
            uiScope.launch {
                val user1 = database.getAccountByUsername(chatroom.user1)
                holder.photo.setImageBitmap(user1.imageProfile)
            }
        }

        uiScope.launch {
            val lastChat = database.getLastChatByChatRoom(chatroom.id)
            if (lastChat == null) {
                holder.lastChatTv.visibility = View.GONE
                holder.timestampTv.visibility = View.GONE
            } else {
                holder.lastChatTv.text = lastChat.content

                val sdf = SimpleDateFormat("HH:mm")
                val timestamp = Date(lastChat.timestamp)
                holder.timestampTv.text = sdf.format(timestamp)
            }
        }

        holder.layout.setOnClickListener {
            val activity = it.context as MainActivity
            activity.showOrHideBottomNavigationView()
            it.findNavController().navigate(ChatFragmentDirections.actionChatFragmentToChatroomFragment(chatroom.id, user, false))
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val layout = itemView.findViewById<ConstraintLayout>(R.id.chatroom_list_layout)
        val photo = itemView.findViewById<CircleImageView>(R.id.friend_chat_photo)
        val nameTv = itemView.findViewById<TextView>(R.id.friend_chat_name_tv)
        val lastChatTv = itemView.findViewById<TextView>(R.id.friend_chat_content_tv)
        val timestampTv = itemView.findViewById<TextView>(R.id.friend_chat_timestamp_tv)
    }
}