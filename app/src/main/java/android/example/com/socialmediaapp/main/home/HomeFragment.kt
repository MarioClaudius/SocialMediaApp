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
import androidx.lifecycle.get
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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

        val viewModelFactory = HomeViewModelFactory(application, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        binding.rvFriendList.layoutManager = LinearLayoutManager(activity)
        binding.rvFriendRequestList.layoutManager = LinearLayoutManager(activity)

        val friendAdapter = FriendsAdapter()
        binding.rvFriendList.adapter = friendAdapter

        val friendRequestAdapter = FriendRequestsAdapter()
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
                    binding.friendRequestsTv.visibility = View.GONE
                    binding.rvFriendRequestList.visibility = View.GONE
                }
                else {
                    binding.friendRequestsTv.visibility = View.VISIBLE
                    binding.rvFriendRequestList.visibility = View.VISIBLE
                    binding.friendRequestsTv.text = "Friend Request " + it.size.toString()
                }
            }
        })
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