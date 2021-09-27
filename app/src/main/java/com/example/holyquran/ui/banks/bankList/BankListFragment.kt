package com.example.holyquran.ui.banks.bankList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.data.model.Bank
import com.example.holyquran.databinding.FragmentBankListBinding
import com.example.holyquran.ui.userList.UserListFragmentDirections
import com.google.android.material.snackbar.Snackbar

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
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory = ViewModelProviderFactory(personalDAO, transactionDAO,loanDAO, bankDAO,application)
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
        val mBankAdapter = BankAdapter()
        mBankAdapter.setOnclickListener(AdapterListener({
            if (it != 0L)
//                this.findNavController().navigate(
//                    UserListFragmentDirections.actionUserListFragmentToIncreaseMoneyFragment(it)
//                )
            Log.d("TAG", "navTeat $it ")

        }, {
            deleteDialog(it)
        }
        ))
        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBankListBinding.rvFeed.adapter = mBankAdapter
        mBankListBinding.rvFeed.layoutManager = mLinearLayoutManager
        bankInfo()
        return mBankListBinding.root
    }

    private fun deleteDialog(bankInfo: Bank) {
        Snackbar.make(mBankListBinding.root, "آیا تمایل به حذف این بانک دارید؟ ", Snackbar.LENGTH_LONG)
            .setAction("حذف") {
                mBankListViewModel.deleteCategory(bankInfo)
            }
            .setActionTextColor(resources.getColor(android.R.color.holo_red_light))
            .show()

    }
    private fun bankInfo() {
        mBankListViewModel.getBankList().observe(viewLifecycleOwner, {
            mBankListViewModel.bankInfo.value = it
            Log.d("TAG", "viewHolder: ${it.size}")
        })
    }

}
