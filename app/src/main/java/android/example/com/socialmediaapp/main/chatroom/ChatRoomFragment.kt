package android.example.com.socialmediaapp.main.chatroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.databinding.FragmentChatRoomBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

class ChatRoomFragment : Fragment() {

    private lateinit var binding: FragmentChatRoomBinding

    private lateinit var viewModel: ChatRoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentChatRoomBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val viewModelFactory = ChatRoomViewModelFactory(application, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ChatRoomViewModel::class.java)

        binding.rvChatList.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }
}