package com.example.holyquran.ui.mainPage

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import androidx.navigation.fragment.findNavController


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

        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, application)
        mMainViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(MainFragmentViewModel::class.java)
        mMainPageBinding.viewModel = mMainViewModel
        this.also { mMainPageBinding.lifecycleOwner = it }

        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            AlertDialog()
        }
        return mMainPageBinding.root
    }


    fun AlertDialog() {
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