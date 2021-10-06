package com.example.holyquran.ui.loan.loanList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentLoanListBinding
import com.example.holyquran.ui.userList.UserListFragmentDirections

class LoanListFragment : Fragment() {
    lateinit var mLoanListBinding: FragmentLoanListBinding
    lateinit var mLoanListViewModel: LoanListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLoanListBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_loan_list, container, false)


        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, bankDAO, application)
        mLoanListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(LoanListViewModel::class.java)
        mLoanListBinding.viewModel = mLoanListViewModel
        this.also { mLoanListBinding.lifecycleOwner = it }


        val mLoanListAdapter = LoanListAdapter()
        mLoanListAdapter.setOnclickListener(AdapterListener({
            if (it != 0L)
                this.findNavController().navigate(
                    LoanListFragmentDirections.actionLoanListFragmentToLoanDetailFragment(it)
                )
            Log.d("TAG", "navTeat $it ")

        }, {

        }
        ))
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mLoanListBinding.rvFeed.adapter = mLoanListAdapter
        mLoanListBinding.rvFeed.layoutManager = mLinearLayoutManager
        loanInfo()
        return mLoanListBinding.root
    }

    private fun loanInfo() {
        mLoanListViewModel.getAllLoanList().observe(viewLifecycleOwner, {
            mLoanListViewModel.loanInfo.value = it
            Log.d("TAG", "viewHolder: $it")
        })
    }
}