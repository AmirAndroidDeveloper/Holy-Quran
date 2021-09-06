package com.example.holyquran.ui.userList

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
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentUserListBinding
import com.example.holyquran.ui.mainPage.MainFragmentViewModel

class UserListFragment : Fragment() {
    lateinit var mUserListBinding: FragmentUserListBinding
    lateinit var mUserListViewModel: UserListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUserListBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_user_list,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, application)
        mUserListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(UserListViewModel::class.java)
        mUserListBinding.viewModel = mUserListViewModel
        this.also { mUserListBinding.lifecycleOwner = it }

        mUserListViewModel.goTOAddUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Toast.makeText(activity, "Login", Toast.LENGTH_SHORT).show()
                this.findNavController().navigate(
                    UserListFragmentDirections.actionUserListFragmentToAddUserFragment())
            }
        })
        return mUserListBinding.root


    }
}
//            val userList: MutableList<UserInfo> = mutableListOf()
//            if (it) {
//                mUserListViewModel.insertUser(
//                    mRegisterUserBinding.fullName.text.toString(),
//                    mRegisterUserBinding.phoneNumber.text.toString(),
//                    mRegisterUserBinding.personalCode.text.toString(),
//                    mRegisterUserBinding.address.text.toString(),
//                    userList
//                )
//            }
//        }

//        })