package com.example.holyquran.ui.editUserInfo

import android.os.Bundle
import android.util.Log
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
import com.example.holyquran.databinding.FragmentEditBinding
import com.google.android.material.snackbar.Snackbar

class EditUserInfoFragment : Fragment() {
    lateinit var mEditFragmentBiding: FragmentEditBinding
    lateinit var mEditUserInfoViewModel: EditUserInfoViewModel
    var id = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mEditFragmentBiding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_edit, container, false)

        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, bankDAO, application)

        mEditUserInfoViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(EditUserInfoViewModel::class.java)
        mEditFragmentBiding.viewModel = mEditUserInfoViewModel
        this.also { mEditFragmentBiding.lifecycleOwner = it }
        val arg =
            EditUserInfoFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userId
        subscription()
        return mEditFragmentBiding.root
    }

    var userInfo = UserInfo(0L, "", null, "", "", null, "");
    private fun subscription() {
        mEditUserInfoViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mEditUserInfoViewModel.setUserName(it)
            userInfo = it
        })
        mEditUserInfoViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mEditFragmentBiding.userInfo = it
            }
        })
        mEditUserInfoViewModel.validInfo.observe(viewLifecycleOwner, Observer {
            if (it) {
                if (validInfo()) {
                    userInfo.fullName = mEditFragmentBiding.userName.text.toString()
                    userInfo.mobileNumber = mEditFragmentBiding.mobileNumber.text.toString()
                    userInfo.phoneNumber = mEditFragmentBiding.phoneNumber.text.toString()
                    userInfo.address = mEditFragmentBiding.address.text.toString()
                    mEditUserInfoViewModel.updateUser(
                        userInfo
                    )
                    Toast.makeText(activity, "update user", Toast.LENGTH_LONG).show();
                }
                mEditUserInfoViewModel.validDone()
            }
        })
        mEditUserInfoViewModel.updateSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack();
                Toast.makeText(activity, "ویرایش با موفقیت دخیره گردید", Toast.LENGTH_LONG).show();
            }
        })
    }

    private fun validInfo(): Boolean {
        if (mEditFragmentBiding.userName.text.isEmpty()) {


            Snackbar.make(
                mEditFragmentBiding.root,
                resources.getText(R.string.empty_user_field_error),
                Snackbar.LENGTH_LONG
            )
                .setAction(resources.getText(R.string.submit)) {
                }
                .setActionTextColor(resources.getColor(android.R.color.white))
                .show()
            return false
        } else if (mEditFragmentBiding.mobileNumber.text.isEmpty()) {
            return true
        } else if (mEditFragmentBiding.phoneNumber.text.isEmpty()) {
            return true

        } else if (mEditFragmentBiding.address.text.isEmpty()) {
            return true

        }
        return false
    }
}