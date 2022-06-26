package android.example.com.socialmediaapp.main.chatroom

import android.app.Activity
import android.content.Context
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.databinding.FragmentChatRoomBinding
import android.example.com.socialmediaapp.main.MainActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager


class ChatRoomFragment : Fragment() {

    private lateinit var binding: FragmentChatRoomBinding

    private lateinit var viewModel: ChatRoomViewModel

    private lateinit var mActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatRoomBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val args = ChatRoomFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = ChatRoomViewModelFactory(application, dataSource, args.roomId, args.user1)

        mActivity.supportActionBar?.title = args.user2

        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatRoomViewModel::class.java)

        binding.rvChatList.layoutManager = LinearLayoutManager(activity)

        val chatContentAdapter = ChatContentAdapter(dataSource, args.user1)
        binding.rvChatList.adapter = chatContentAdapter

        viewModel.chatContentList.observe(viewLifecycleOwner, Observer {
            it.let {
                chatContentAdapter.data = it
            }
        })

        binding.sendChatButton.setOnClickListener {
            val chatContent = binding.edittextChat.text.toString()
            viewModel.sendChat(chatContent)
            binding.edittextChat.text.clear()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val activity = activity as MainActivity
                activity.showOrHideBottomNavigationView()
                if(args.fromHome) {
                    findNavController().navigate(ChatRoomFragmentDirections.actionChatroomFragmentToHomeFragment())
                } else {
                    findNavController().navigate(ChatRoomFragmentDirections.actionChatroomFragmentToChatFragment())
                }
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is Activity) {
            mActivity = context as MainActivity
        }
    }
}