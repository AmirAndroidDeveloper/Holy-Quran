package com.example.holyquran.ui.userList.userOptions

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentUserOptionsBinding

class UserOptionsFragment : Fragment() {
    var id: Long = 0L
    lateinit var mUserOptionsBinding: FragmentUserOptionsBinding
    lateinit var mUserOptionViewModel: UserOptionViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUserOptionsBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_user_options,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val viewModelFactory = ViewModelProviderFactory(userDAO, transactionDAO, application)

        mUserOptionViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(UserOptionViewModel::class.java)
        val arg = UserOptionsFragmentArgs.fromBundle(requireArguments())
        id = arg.userId
        Log.d("TAG", "onCreateView: $id")
        mUserOptionViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mUserOptionViewModel.setUserName(it)
        })
        mUserOptionViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mUserOptionsBinding.userName = it
            }
        })

        mUserOptionsBinding.finishBtn.setOnClickListener {
            mUserOptionViewModel.insertMoney(
                mUserOptionsBinding.increaseEDT.text.toString(),
                id
            )
        }
        return mUserOptionsBinding.root
    }

}