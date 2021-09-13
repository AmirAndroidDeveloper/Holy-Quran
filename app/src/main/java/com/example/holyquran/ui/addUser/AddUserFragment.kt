package com.example.holyquran.ui.addUser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.UserInfo
import com.example.holyquran.databinding.FragmentAddUserBinding
import com.example.holyquran.databinding.FragmentUserListBinding
import com.example.holyquran.ui.userList.UserListFragmentDirections
import com.example.holyquran.ui.userList.UserListViewModel

class AddUserFragment : Fragment() {
    lateinit var mAddUserListBinding: FragmentAddUserBinding
    lateinit var mAddUserViewModel: AddUserViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mAddUserListBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_user, container, false)

        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO,transactionDAO,loanDAO,application)
        mAddUserViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(AddUserViewModel::class.java)
        mAddUserListBinding.viewModel = mAddUserViewModel
        this.also { mAddUserListBinding.lifecycleOwner = it }


        mAddUserViewModel.addUser.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                val userList: MutableList<UserInfo> = mutableListOf()
                mAddUserViewModel.insertUser(
                    mAddUserListBinding.edtFullName.text.toString(),
                    mAddUserListBinding.accountId.text.toString(),
                    mAddUserListBinding.edtMobileNumber.text.toString(),
                    mAddUserListBinding.edtPhoneNumber.text.toString(),
                    mAddUserListBinding.edtDateOfCreation.text.toString(),
                    mAddUserListBinding.edtAddress.text.toString(),
                    userList
                )
                this.findNavController().navigate(
                    AddUserFragmentDirections.actionAddUserFragmentToUserListFragment())

            }
        })

        return mAddUserListBinding.root

    }

    }



