package android.example.com.socialmediaapp.main.home

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.database.entities.Friendship
import android.example.com.socialmediaapp.database.entities.FriendshipStatus
import android.util.Log
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

class FriendRequestsAdapter(
    private val database: SocialMediaDatabaseDao
): RecyclerView.Adapter<FriendRequestsAdapter.ViewHolder>() {

    var data = listOf<Friendship>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_friend_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        val item = data[position]
        holder.nickname.text = item.friend
        holder.acceptButton.setOnClickListener {
            uiScope.launch {
                val firstFriendship = database.getPendingFriendRequestByUserAndFriend(item.user, item.friend)
                firstFriendship.status = FriendshipStatus.ACTIVE
                database.updateFriendRequests(firstFriendship)
                val secondFriendship = Friendship(user = item.friend, friend = item.user, status = FriendshipStatus.ACTIVE)
                database.insertActiveFriendRequest(secondFriendship)
            }
        }
        holder.rejectButton.setOnClickListener {
            uiScope.launch {
                val firstFriendship = database.getPendingFriendRequestByUserAndFriend(item.user, item.friend)
                firstFriendship.status = FriendshipStatus.REJECTED
                database.updateFriendRequests(firstFriendship)
            }
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