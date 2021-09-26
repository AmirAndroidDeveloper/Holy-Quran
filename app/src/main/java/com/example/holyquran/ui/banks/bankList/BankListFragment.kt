package com.example.holyquran.ui.banks.bankList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentBankListBinding
import com.example.holyquran.ui.userList.UserListFragmentDirections
import com.example.holyquran.ui.userList.UserListViewModel

class BankListFragment : Fragment() {
lateinit var mBankListBinding:FragmentBankListBinding
lateinit var mBankListViewModel: BankListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
mBankListBinding=
    DataBindingUtil.inflate(layoutInflater,R.layout.fragment_bank_list, container, false)

        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, transactionDAO,loanDAO, application)
        mBankListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(BankListViewModel::class.java)
        mBankListBinding.viewModel = mBankListViewModel
        this.also { mBankListBinding.lifecycleOwner = it }

        mBankListViewModel.goTOAddBank.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    BankListFragmentDirections.actionBankListFragmentToAddBankFragment()
                )
                mBankListViewModel.goTOAddBankDone()
            }
        })
    return mBankListBinding.root
    }
}