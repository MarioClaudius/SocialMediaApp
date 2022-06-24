package android.example.com.socialmediaapp.main.home

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
import android.example.com.socialmediaapp.database.entities.ChatRoom
import android.example.com.socialmediaapp.databinding.FriendDetailDialogBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FriendDetailDialog(
    private val database: SocialMediaDatabaseDao,
    private val user1: String
) : DialogFragment() {
    private lateinit var binding: FriendDetailDialogBinding

    companion object {
        const val TAG = "FriendDetailDialog"

        private const val KEY_PHOTO_IMAGE = "KEY_PHOTO_IMAGE"
        private const val KEY_NICKNAME = "KEY_NICKNAME"

        fun newInstance(photo: Int, user1: String, nickname: String, database: SocialMediaDatabaseDao): FriendDetailDialog {
            val args = Bundle()
            args.putInt(KEY_PHOTO_IMAGE, photo)
            args.putString(KEY_NICKNAME, nickname)
            val fragment = FriendDetailDialog(database, user1)
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
        val friendName = arguments?.getString(KEY_NICKNAME)!!
        binding.friendDetailPhoto.setImageResource(arguments?.getInt(KEY_PHOTO_IMAGE)!!)
        binding.friendDetailNickname.text = friendName
        binding.friendDetailChatButton.setOnClickListener {
            var viewModelJob = Job()
            val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
            val chatroom = ChatRoom(user1 = user1, user2 = friendName)
            uiScope.launch {
                database.insertChatRoom(chatroom)
            }
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToChatroomFragment(chatroom.id, user1))
//            findNavController().navigate(R.id.action_homeFragment_to_chatroomFragment)
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