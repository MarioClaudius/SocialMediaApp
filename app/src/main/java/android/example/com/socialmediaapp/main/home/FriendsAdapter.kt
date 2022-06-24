package android.example.com.socialmediaapp.main.home

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.Account
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView


class FriendsAdapter(
    private val database: SocialMediaDatabaseDao,
    private val user1: String,
    private val listener: HomeFragment
): RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

    var data = listOf<Account>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_friend, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val manager = (holder.nickname.context as AppCompatActivity).supportFragmentManager
        holder.nickname.text = item.username
        holder.layout.setOnClickListener {
            FriendDetailDialog.newInstance(R.drawable.failed_logo, user1, item.username, database, listener).show(manager, FriendDetailDialog.TAG)
        }
    }

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val layout = itemView.findViewById<ConstraintLayout>(R.id.friend_detail_layout)
        val photo = itemView.findViewById<CircleImageView>(R.id.friend_photo)
        val nickname = itemView.findViewById<TextView>(R.id.friend_nickname_tv)
    }

}