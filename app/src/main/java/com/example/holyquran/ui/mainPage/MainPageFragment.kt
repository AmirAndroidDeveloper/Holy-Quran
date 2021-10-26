package com.example.holyquran.ui.mainPage

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toolbar
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding


class MainPageFragment : Fragment() {
    var id: Long = 0L
    lateinit var mMainPageBinding: FragmentMainPageBinding
    lateinit var mMainViewModel: MainFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mMainPageBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main_page, container, false)
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, bankDAO, application)
        mMainViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(MainFragmentViewModel::class.java)
        mMainPageBinding.viewModel = mMainViewModel
        this.also { mMainPageBinding.lifecycleOwner = it }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { alertDialog() }

        mMainViewModel.getUserList().observe(viewLifecycleOwner, {
            mMainViewModel.userInfo.value = it
            mMainPageBinding.currentUsers.text = it.size.toString()
        })
        mMainViewModel.getLoanList().observe(viewLifecycleOwner, {
            mMainViewModel.loan.value = it
            mMainPageBinding.loans.text = it.size.toString()
        })

        return mMainPageBinding.root
    }

    private fun alertDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        builder.setIcon(R.drawable.warning)
        builder.setTitle("خروج از برنامه ")
        builder.setMessage("از برنامه خارج میشوید؟")
            .setCancelable(false)
            .setPositiveButton("بله",
                DialogInterface.OnClickListener { dialog, id -> System.exit(0) })
            .setNegativeButton("خیر",
                DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert: AlertDialog = builder.create()
        alert.show()

    }

}
