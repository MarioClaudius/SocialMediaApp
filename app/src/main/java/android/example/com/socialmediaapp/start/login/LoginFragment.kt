package android.example.com.socialmediaapp.start.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.databinding.FragmentLoginBinding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val viewModelFactory = LoginViewModelFactory(application, dataSource)

        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.lifecycleOwner = this

        binding.loginButton.setOnClickListener {
            val usernameOrEmail = binding.emailEdt.text.toString()
            val password = binding.passwordEdt.text.toString()
            viewModel.login(usernameOrEmail, password)
        }

        binding.registerClickTv.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        return binding.root
    }
}