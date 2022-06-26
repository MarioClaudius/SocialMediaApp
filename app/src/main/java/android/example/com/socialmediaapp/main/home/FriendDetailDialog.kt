package android.example.com.socialmediaapp.main.home

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.ChatRoom
import android.example.com.socialmediaapp.databinding.FriendDetailDialogBinding
import android.example.com.socialmediaapp.main.MainActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*

class FriendDetailDialog(
    private val database: SocialMediaDatabaseDao,
    private val user1: String,
    private val listener: HomeFragment
) : DialogFragment() {
    private lateinit var binding: FriendDetailDialogBinding

    companion object {
        const val TAG = "FriendDetailDialog"

        private const val KEY_PHOTO_IMAGE = "KEY_PHOTO_IMAGE"
        private const val KEY_NICKNAME = "KEY_NICKNAME"

        fun newInstance(photo: Int, user1: String, nickname: String, database: SocialMediaDatabaseDao, listener: HomeFragment): FriendDetailDialog {
            val args = Bundle()
            args.putInt(KEY_PHOTO_IMAGE, photo)
            args.putString(KEY_NICKNAME, nickname)
            val fragment = FriendDetailDialog(database, user1, listener)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FriendDetailDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
        val friendName = arguments?.getString(KEY_NICKNAME)!!
        binding.friendDetailPhoto.setImageResource(arguments?.getInt(KEY_PHOTO_IMAGE)!!)
        binding.friendDetailNickname.text = friendName

        uiScope.launch {
            val friend = database.getAccountByUsername(friendName)
            binding.friendDetailPhoto.setImageBitmap(friend.imageProfile)
        }

        binding.friendDetailChatButton.setOnClickListener {
            var chatroom: ChatRoom
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    if (database.getChatRoomByUserAndFriend(user1, friendName) == null) {
                        val room = ChatRoom(user1 = user1, user2 = friendName)
                        database.insertChatRoom(room)
                    }
                    chatroom = database.getChatRoomByUserAndFriend(user1, friendName)
                    Log.i("CHATROOM", "RoomId = ${chatroom.id}, user1 = ${chatroom.user1}, user2 = ${chatroom.user2}")

                    withContext(Dispatchers.Main) {
                        val activity: MainActivity = listener.activity as MainActivity
                        listener.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatroomFragment(chatroom.id, user1, friendName,true))
                        activity.showOrHideBottomNavigationView()
                    }
                }
            }
            dismiss()       // ditambahin navigation ke fragment chat
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}