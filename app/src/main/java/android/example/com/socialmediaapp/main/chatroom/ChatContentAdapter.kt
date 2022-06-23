package android.example.com.socialmediaapp.main.chatroom

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.entities.Chat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*

class ChatContentAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        val chat = data.get(position)

        if (chat.userId.equals("ID SENDER")) {
            return VIEW_TYPE_MESSAGE_SENT
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    private class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    private class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextReceived = itemView.findViewById<TextView>(R.id.text_other_chat_bubble)
        val profileNameText = itemView.findViewById<TextView>(R.id.text_profile_name_other)
        val profilePhoto = itemView.findViewById<CircleImageView>(R.id.image_profile_other)
        val timestampText = itemView.findViewById<TextView>(R.id.text_other_timestamp)

        fun bind(chat: Chat) {
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