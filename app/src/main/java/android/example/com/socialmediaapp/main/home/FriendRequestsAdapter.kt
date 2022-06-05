package android.example.com.socialmediaapp.main.home

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.entities.Account
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView

class FriendRequestsAdapter: RecyclerView.Adapter<FriendRequestsAdapter.ViewHolder>() {

    var data = listOf<Account>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendRequestsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_friend_request, parent, false)
        return FriendRequestsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendRequestsAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.nickname.text = item.username
        holder.acceptButton.isClickable = true
        holder.rejectButton.isClickable = true
        holder.acceptButton.setOnClickListener {
            Toast.makeText(it.context, "ACCEPTED " + item.username, Toast.LENGTH_SHORT)
        }
        holder.rejectButton.setOnClickListener {
            Toast.makeText(it.context, "REJECTED " + item.username, Toast.LENGTH_SHORT)
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val photo = itemView.findViewById<CircleImageView>(R.id.friend_request_photo)
        val nickname = itemView.findViewById<TextView>(R.id.friend_request_nickname_tv)
        val acceptButton = itemView.findViewById<CircleImageView>(R.id.accept_button)
        val rejectButton = itemView.findViewById<CircleImageView>(R.id.reject_button)
    }
}