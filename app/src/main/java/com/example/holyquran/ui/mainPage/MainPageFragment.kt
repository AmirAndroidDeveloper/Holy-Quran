package com.example.holyquran.ui.mainPage

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import androidx.transition.TransitionManager
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyFragmentArgs


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

       requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) { alertDialog() }
        return mMainPageBinding.root
    }


    fun alertDialog() {
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