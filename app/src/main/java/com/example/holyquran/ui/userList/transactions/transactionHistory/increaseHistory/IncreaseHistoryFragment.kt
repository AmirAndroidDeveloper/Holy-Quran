package com.example.holyquran.ui.userList.transactions.transactionHistory.increaseHistory

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
import com.example.holyquran.databinding.FragmentIncreaseHistoryBinding
import com.example.holyquran.ui.mainPage.MainFragmentViewModel
import com.example.holyquran.ui.userList.UserListFragmentDirections
import com.example.holyquran.ui.userList.transactions.increaseMoney.IncreaseMoneyFragmentArgs
import net.objecthunter.exp4j.ExpressionBuilder

class IncreaseHistoryFragment : Fragment() {
    lateinit var mIncreaseHistoryBinding: FragmentIncreaseHistoryBinding
    lateinit var mIncreaseHistoryViewModel: IncreaseHistoryViewModel
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
        val viewModelFactory = ViewModelProviderFactory(personalDAO, transactionDAO, application)
        mIncreaseHistoryViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(IncreaseHistoryViewModel::class.java)
        mIncreaseHistoryBinding.increaseHistoryViewModel = mIncreaseHistoryViewModel
        this.also { mIncreaseHistoryBinding.lifecycleOwner = it }

        val arg =
            IncreaseHistoryFragmentArgs.fromBundle(
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
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mIncreaseHistoryBinding.rvFeed.adapter = mIncreaseHistoryAdapter
        mIncreaseHistoryBinding.rvFeed.layoutManager = mLinearLayoutManager
        userInfo()


        mIncreaseHistoryViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mIncreaseHistoryViewModel.setUserName(it)
        })
        mIncreaseHistoryViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mIncreaseHistoryBinding.userName = it
            }
        })
        return mIncreaseHistoryBinding.root
    }

    private fun userInfo() {
        mIncreaseHistoryViewModel.getTransactionList(id).observe(viewLifecycleOwner, {
            mIncreaseHistoryViewModel.transactionInfo.value = it
            Log.d("TAG", "viewHolder: $it")
        })
    }
}

