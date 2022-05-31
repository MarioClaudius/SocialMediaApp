package android.example.com.socialmediaapp.start.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.example.com.socialmediaapp.R
import android.example.com.socialmediaapp.database.SocialMediaDatabase
import android.example.com.socialmediaapp.database.entities.Account
import android.example.com.socialmediaapp.databinding.FragmentRegisterBinding
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SocialMediaDatabase.getInstance(application).socialMediaDatabaseDao

        val viewModelFactory = RegisterViewModelFactory(application, dataSource)

        Log.i("RegisterFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.lifecycleOwner = this

        binding.registerButton.setOnClickListener {
            val username = binding.registerUsernameEdt.text.toString()
            val email = binding.registerEmailEdt.text.toString()
            val password = binding.registerPasswordEdt.text.toString()
            viewModel.register(username, email, password)
        }

        viewModel.errorInput.observe(viewLifecycleOwner, Observer { error ->
            if (error) {
                Toast.makeText(context, "INPUT ERROR", Toast.LENGTH_SHORT).show()
                viewModel.doneToastErrorInput()
            }
        })

        viewModel.accountIsExist.observe(viewLifecycleOwner, Observer { error ->
            if (error) {
                Toast.makeText(context, "AKUN SUDAH TERDAFTAR", Toast.LENGTH_SHORT).show()
                viewModel.doneToastAccountIsExist()
            }
        })

        viewModel.registerIsSuccess.observe(viewLifecycleOwner, Observer { isSuccess ->
            if (isSuccess) {
                Toast.makeText(context, "AKUN BERHASIL DIDAFTARKAN", Toast.LENGTH_SHORT).show()
                viewModel.doneToastRegisterIsSuccess()
            }
        })

        return binding.root
    }
}