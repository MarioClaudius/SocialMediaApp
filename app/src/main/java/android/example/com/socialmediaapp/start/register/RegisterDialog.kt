package android.example.com.socialmediaapp.start.register

import android.example.com.socialmediaapp.databinding.FragmentRegisterDialogBinding
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment

class RegisterDialog : DialogFragment() {

    private lateinit var binding: FragmentRegisterDialogBinding

    companion object {
        const val TAG = "RegisterDialog"

        private const val KEY_IMAGE = "KEY_IMAGE"
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_SUBTITLE = "KEY_SUBTITLE"

        fun newInstance(image: Int, title: String, subTitle: String): RegisterDialog {
            val args = Bundle()
            args.putInt(KEY_IMAGE, image)
            args.putString(KEY_TITLE, title)
            args.putString(KEY_SUBTITLE, subTitle)
            val fragment = RegisterDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.successOrFailLogo.setImageResource(arguments?.getInt(KEY_IMAGE)!!)
        binding.dialogTitle.text = arguments?.getString(KEY_TITLE)
        binding.dialogSubtitle.text = arguments?.getString(KEY_SUBTITLE)
        if (arguments?.getString(KEY_TITLE)?.equals("Register Failed")!!) {
            binding.button.setBackgroundColor(Color.rgb(198, 40, 40))
            binding.button.text = "Retry"
        } else {
            binding.button.setBackgroundColor(Color.rgb(109, 208, 24))
            binding.button.text = "OK"
        }
        binding.button.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

}