package android.example.com.socialmediaapp.main.chatroom

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Chat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ChatContentAdapter(
    val database: SocialMediaDatabaseDao,
    private val sender: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_MESSAGE_SENT = 1;
        const val VIEW_TYPE_MESSAGE_RECEIVED = 2;
    }

    var data = listOf<Chat>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.self_chat_bubble_layout, parent, false)
            SentMessageHolder(view)
        } else { // viewType == VIEW_TYPE_MESSAGE_RECEIVED
            val view = LayoutInflater.from(parent.context).inflate(R.layout.other_chat_bubble_layout, parent, false)
            ReceivedMessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = data.get(position)

        when(holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(chat)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(chat)
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        val chat = data.get(position)

        if (chat.userId.equals(sender)) {
            return VIEW_TYPE_MESSAGE_SENT
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    private inner class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextSent = itemView.findViewById<TextView>(R.id.text_self_chat_bubble)
        val timestampText = itemView.findViewById<TextView>(R.id.text_self_timestamp)

        fun bind(chat: Chat) {
            messageTextSent.text = chat.content

            // convert timestamp to date format
            val sdf = SimpleDateFormat("HH:mm")
            val timestamp = Date(chat.timestamp)
            timestampText.text = sdf.format(timestamp)
        }
    }

    private inner class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextReceived = itemView.findViewById<TextView>(R.id.text_other_chat_bubble)
        val profileNameText = itemView.findViewById<TextView>(R.id.text_profile_name_other)
        val profilePhoto = itemView.findViewById<CircleImageView>(R.id.image_profile_other)
        val timestampText = itemView.findViewById<TextView>(R.id.text_other_timestamp)

        fun bind(chat: Chat) {
            var viewModelJob = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

            uiScope.launch {
                val receiver = database.getAccountByUsername(chat.userId)
                profilePhoto.setImageBitmap(receiver.imageProfile)
            }

            messageTextReceived.text = chat.content
            profileNameText.text = chat.userId
            profilePhoto.setImageResource(R.drawable.failed_logo_no_bg)     // still static

            // convert timestamp to date format
            val sdf = SimpleDateFormat("HH:mm")
            val timestamp = Date(chat.timestamp)
            timestampText.text = sdf.format(timestamp)
        }
    }
}