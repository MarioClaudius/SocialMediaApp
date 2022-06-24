package android.example.com.socialmediaapp.main.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.databinding.FragmentChatBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding

    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val user = requireActivity().intent.getStringExtra("id")!!

        val viewModelFactory = ChatViewModelFactory(application, dataSource, user)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatViewModel::class.java)

        binding.rvChatroomList.layoutManager = LinearLayoutManager(activity)

        val chatRoomListAdapter = ChatRoomListAdapter(dataSource, user)
        binding.rvChatroomList.adapter = chatRoomListAdapter

        viewModel.chatRoomList.observe(viewLifecycleOwner, Observer {
            it.let {
                chatRoomListAdapter.data = it
            }
        })

        return binding.root
    }
}