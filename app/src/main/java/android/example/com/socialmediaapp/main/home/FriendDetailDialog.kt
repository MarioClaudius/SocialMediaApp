package android.example.com.socialmediaapp.main.home

import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabaseDao
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

class FriendDetailDialog() : DialogFragment() {
    private lateinit var binding: FriendDetailDialogBinding

    companion object {
        const val TAG = "FriendDetailDialog"

        private const val KEY_PHOTO_IMAGE = "KEY_PHOTO_IMAGE"
        private const val KEY_NICKNAME = "KEY_NICKNAME"

        fun newInstance(photo: Int, nickname: String): FriendDetailDialog {
            val args = Bundle()
            args.putInt(KEY_PHOTO_IMAGE, photo)
            args.putString(KEY_NICKNAME, nickname)
            val fragment = FriendDetailDialog()
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
        binding.friendDetailPhoto.setImageResource(arguments?.getInt(FriendDetailDialog.KEY_PHOTO_IMAGE)!!)
        binding.friendDetailNickname.text = arguments?.getString(FriendDetailDialog.KEY_NICKNAME)
        binding.friendDetailChatButton.setOnClickListener {
            dismiss()       // ditambahin navigation ke fragment chat
            findNavController().navigate(R.id.action_homeFragment_to_chatroomFragment)
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