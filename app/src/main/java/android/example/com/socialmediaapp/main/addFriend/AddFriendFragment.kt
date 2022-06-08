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
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [AddFriendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFriendFragment : Fragment() {

    private lateinit var binding: FragmentAddFriendBinding

    private lateinit var viewModel: AddFriendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val viewModelFactory = AddFriendViewModelFactory(application, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(AddFriendViewModel::class.java)

        binding.searchFriendButton.setOnClickListener {
            val username = binding.friendSearchEdt.text.toString()
            viewModel.getAccountByUsername(username)
        }

        binding.friendSearchAddButton.setOnClickListener {
            val username = requireActivity().intent.getStringExtra("id")!!
            val friend = viewModel.friendAccount.value!!.username
            val friendship = Friendship(user = username, friend = friend, status = FriendshipStatus.PENDING)
            viewModel.insertFriendship(friendship)
            Log.i("AddFriendFragment", "INSERT BERHASIL")
        }

        viewModel.friendAccount.observe(viewLifecycleOwner, Observer { friendAccount ->
            if(friendAccount == null) {
                binding.accountNotFoundDetail.visibility = View.VISIBLE
                binding.friendSearchAccountDetail.visibility = View.GONE
            } else {
                binding.friendSearchNickname.text = friendAccount.username
                binding.accountNotFoundDetail.visibility = View.GONE
                binding.friendSearchAccountDetail.visibility = View.VISIBLE
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