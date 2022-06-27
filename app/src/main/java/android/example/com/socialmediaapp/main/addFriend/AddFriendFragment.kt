package android.example.com.socialmediaapp.main.addFriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.database.entities.Friendship
import android.example.com.socialmediaapp.database.entities.FriendshipStatus
import android.example.com.socialmediaapp.databinding.FragmentAddFriendBinding
import android.example.com.socialmediaapp.main.MainActivity
import android.graphics.Color
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay

class AddFriendFragment : Fragment() {

    private lateinit var binding: FragmentAddFriendBinding

    private lateinit var viewModel: AddFriendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)

        val user = requireActivity().intent.getStringExtra("id")!!

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val viewModelFactory = AddFriendViewModelFactory(application, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddFriendViewModel::class.java)

        binding.searchFriendButton.setOnClickListener {
            val friend = binding.friendSearchEdt.text.toString()
            viewModel.getAccountByUsername(friend)
            viewModel.checkAccountFriendRequest(user, friend)
        }

        binding.friendSearchAddButton.setOnClickListener {
            val friend = viewModel.friendAccount.value!!.username

            if (binding.friendSearchAddButton.text.toString().equals("Chat")) {
                viewModel.getChatroomByUserAndFriend(user, friend)
            } else {
                val friendship = Friendship(user = user, friend = friend, status = FriendshipStatus.PENDING)
                viewModel.insertFriendship(friendship)
                Log.i("AddFriendFragment", "INSERT BERHASIL")
            }
        }

        viewModel.chatroom.observe(viewLifecycleOwner, Observer { chatroom ->
            if (chatroom.id != 0L) {
                val friend = viewModel.friendAccount.value!!.username
                findNavController().navigate(AddFriendFragmentDirections.actionAddFriendFragmentToChatroomFragment(chatroom.id, user, friend, false))
            }
        })

        viewModel.friendRequestStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status == FriendshipStatus.PENDING) {
                binding.friendSearchAddButton.text = "Added"
                binding.friendSearchAddButton.isEnabled = false
                binding.friendSearchAddButton.setBackgroundColor(Color.GRAY)
            } else if (status == FriendshipStatus.ACTIVE) {
                binding.friendSearchAddButton.text = "Chat"
                binding.friendSearchAddButton.isEnabled = true
                binding.friendSearchAddButton.setBackgroundColor(Color.rgb(109, 208, 24))
            } else {
                binding.friendSearchAddButton.text = "Add"
                binding.friendSearchAddButton.isEnabled = true
                binding.friendSearchAddButton.setBackgroundColor(Color.rgb(109, 208, 24))
            }
        })

        viewModel.friendAccount.observe(viewLifecycleOwner, Observer { friendAccount ->
            if(friendAccount == null) {
                binding.accountNotFoundDetail.visibility = View.VISIBLE
                binding.friendSearchAccountDetail.visibility = View.GONE
            } else {
                binding.friendSearchNickname.text = friendAccount.username
                binding.accountNotFoundDetail.visibility = View.GONE
                binding.friendSearchAccountDetail.visibility = View.VISIBLE
                binding.friendSearchPhoto.setImageBitmap(friendAccount.imageProfile)
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val activity = activity as MainActivity
                activity.showOrHideBottomNavigationView()
                findNavController().navigate(R.id.action_addFriendFragment_to_homeFragment)
            }
        })
        return binding.root
    }


}