package com.example.holyquran.ui.userList.transactions.loan.loanDetails

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentLoanDetailBinding
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import android.widget.EditText
import android.widget.Spinner

class LoanDetailFragment : Fragment() {
    lateinit var mLoanDetailBinding: FragmentLoanDetailBinding
    lateinit var mLoanDetailViewModel: LoanDetailViewModel
    var id = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLoanDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_loan_detail, container, false)

        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, application)

        mLoanDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(LoanDetailViewModel::class.java)
        mLoanDetailBinding.loanDetailViewModel = mLoanDetailViewModel
        this.also { mLoanDetailBinding.lifecycleOwner = it }
        val arg =
            LoanDetailFragmentArgs.fromBundle(
                requireArguments()
            )
        id = arg.loanId

        mLoanDetailViewModel.setLoanDetail(id)?.observe(viewLifecycleOwner, {
            mLoanDetailViewModel.setLoanDetail(it)
        })
        mLoanDetailViewModel.loanDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                mLoanDetailBinding.loanInfo = it

                val pdate = PersianDate()
                val pdformater1 = PersianDateFormat("Y/m/d")
                pdformater1.format(pdate.addDate(10, 0, 1))
                mLoanDetailBinding.loanExpiredAt.text = pdformater1.format(pdate)

                Log.d("TAG", "amount: ${it.amount}")
            }
        })


        return mLoanDetailBinding.root
    }

}