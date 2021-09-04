package com.example.holyquran.ui.registerUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentRegisterUserBinding

class RegisterUserFragment : Fragment() {
    lateinit var mRegisterUserBinding: FragmentRegisterUserBinding
    lateinit var mRegisterUserViewModel: RegisterUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRegisterUserBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_register_user,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, application)
        mRegisterUserViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(RegisterUserViewModel::class.java)
        mRegisterUserBinding.viewModel = mRegisterUserViewModel
        this.also { mRegisterUserBinding.lifecycleOwner = it }

        mRegisterUserViewModel.goToMainPage.observe(viewLifecycleOwner, Observer {
         if (it==true){
            view?.findNavController()
                ?.navigate(R.id.action_registerUserFragment_to_mainPageFragment)
            Toast.makeText(activity,"Login",Toast.LENGTH_SHORT).show()
            val userList: MutableList<UserInfo> = mutableListOf()
            if (it) {
                mRegisterUserViewModel.insertUser(
                    mRegisterUserBinding.edtName.text.toString(),
                    mRegisterUserBinding.edtPassword.editText?.text.toString(),
                    userList
                )
            }
        }

        })
            return mRegisterUserBinding.root
    }


}