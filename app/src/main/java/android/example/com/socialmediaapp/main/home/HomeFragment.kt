package android.example.com.socialmediaapp.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.databinding.FragmentHomeBinding
import android.example.com.socialmediaapp.main.MainActivity
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val username = requireActivity().intent.getStringExtra("id")!!

        val viewModelFactory = HomeViewModelFactory(application, dataSource, username)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.rvFriendList.layoutManager = LinearLayoutManager(activity)
        binding.rvFriendRequestList.layoutManager = LinearLayoutManager(activity)

        val friendAdapter = FriendsAdapter(dataSource, username, this@HomeFragment)
        binding.rvFriendList.adapter = friendAdapter

        val friendRequestAdapter = FriendRequestsAdapter(dataSource)
        binding.rvFriendRequestList.adapter = friendRequestAdapter

        viewModel.friendList.observe(viewLifecycleOwner, Observer {
            it.let {
                friendAdapter.data = it
                binding.friendsTv.text = "Friends " + it.size.toString()
            }
        })

        viewModel.friendRequestList.observe(viewLifecycleOwner, Observer {
            it.let {
                friendRequestAdapter.data = it
                if (it.isEmpty()) {
                    binding.friendRequestTitle.visibility = View.GONE
                    binding.rvFriendRequestList.visibility = View.GONE
                }
                else {
                    binding.friendRequestTitle.visibility = View.VISIBLE
                    binding.rvFriendRequestList.visibility = View.VISIBLE
                    binding.friendRequestsTv.text = "Friend Request " + it.size.toString()
                }
            }
        })

        viewModel.friendListDropDown.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.rvFriendList.visibility = View.VISIBLE
            } else {
                binding.rvFriendList.visibility = View.GONE
            }
        })

        viewModel.friendRequestListDropDown.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.rvFriendRequestList.visibility = View.VISIBLE
            } else {
                binding.rvFriendRequestList.visibility = View.GONE
            }
        })

        binding.arrowFriend.setOnClickListener {
            viewModel.showOrHideFriendList()
            if (viewModel.friendListDropDown.value!!) {
                binding.arrowFriend.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                binding.rvFriendList.visibility = View.VISIBLE
            } else {
                binding.arrowFriend.setImageResource(R.drawable.ic_baseline_arrow_right_24)
                binding.rvFriendList.visibility = View.GONE
            }
        }

        binding.arrowFriendRequest.setOnClickListener {
            viewModel.showOrHideFriendRequestList()
            if (viewModel.friendRequestListDropDown.value!!) {
                binding.arrowFriendRequest.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                binding.rvFriendRequestList.visibility = View.VISIBLE
            } else {
                binding.arrowFriendRequest.setImageResource(R.drawable.ic_baseline_arrow_right_24)
                binding.rvFriendRequestList.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_fragment_app_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_add_friend -> {
                Log.i("HOME FRAGMENT", "MENU ADD DITEKEN")
                val activity: MainActivity = activity as MainActivity
                activity.showOrHideBottomNavigationView()
                requireView().findNavController().navigate(R.id.action_homeFragment_to_addFriendFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}