package android.example.com.socialmediaapp.main.addFriend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.databinding.FragmentAddFriendBinding
import android.example.com.socialmediaapp.main.MainActivity
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [AddFriendFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFriendFragment : Fragment() {

    private lateinit var binding: FragmentAddFriendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddFriendBinding.inflate(inflater, container, false)
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