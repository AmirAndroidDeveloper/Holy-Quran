package com.example.holyquran.ui.transactionHistory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentIncreaseHistoryBinding

class TransactionHistoryFragment : Fragment() {
    lateinit var mIncreaseHistoryBinding: FragmentIncreaseHistoryBinding
    lateinit var mTransactionHistoryViewModel: TransactionHistoryViewModel
    var id: Long = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mIncreaseHistoryBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_increase_history,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, application)
        mTransactionHistoryViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(TransactionHistoryViewModel::class.java)
        mIncreaseHistoryBinding.increaseHistoryViewModel = mTransactionHistoryViewModel
        this.also { mIncreaseHistoryBinding.lifecycleOwner = it }

        val arg =
            TransactionHistoryFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.userId
        val mIncreaseHistoryAdapter = increaseHistoryAdapter()
        mIncreaseHistoryAdapter.setOnclickListener(AdapterListener2({
            if (it != 0L)
                null
        }, {
//            deleteDialog(it)
        }
        ))

        mTransactionHistoryViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mTransactionHistoryViewModel.setUserName(it)
        })
        mTransactionHistoryViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseHistoryBinding.userName = it
            }
        })

        val mLinearLayoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mIncreaseHistoryBinding.rvFeed.adapter = mIncreaseHistoryAdapter
        mIncreaseHistoryBinding.rvFeed.layoutManager = mLinearLayoutManager
        userInfo()
        mTransactionHistoryViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mTransactionHistoryViewModel.setUserName(it)
        })
        mTransactionHistoryViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseHistoryBinding.userName = it
            }
        })

        return mIncreaseHistoryBinding.root

    }

    private fun userInfo() {
        mTransactionHistoryViewModel.getTransactionList(id).observe(viewLifecycleOwner, {
            Log.d("TAG", "userInfo2: ${it}")
            mTransactionHistoryViewModel.transactionInfo.value = it
        })
    }
}

