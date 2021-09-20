package com.example.holyquran.ui.loan.payments

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
import com.example.holyquran.databinding.FragmentLoanPaymentsBinding
import com.example.holyquran.ui.loan.loanDetails.LoanDetailFragmentDirections
import com.example.holyquran.ui.loan.loanHistory.*
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class PaymentsFragment : Fragment() {
    lateinit var mLoanPaymentsBinding: FragmentLoanPaymentsBinding
    lateinit var mPaymentsViewModel: PaymentViewModel
    var id = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLoanPaymentsBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_loan_payments,
                container,
                false
            )
        val application = requireNotNull(this.activity).application
        val personalDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO, application)
        mPaymentsViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(PaymentViewModel::class.java)
        mLoanPaymentsBinding.viewModel = mPaymentsViewModel
        this.also { mLoanPaymentsBinding.lifecycleOwner = it }
        val args = PaymentsFragmentArgs.fromBundle(requireArguments())
        id = args.paymentId

        mPaymentsViewModel.setLoanDetail(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mLoanPaymentsBinding.loan = it
            }
        })
        mPaymentsViewModel.loanDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                mPaymentsViewModel.setLoanDetail(it)

            }
        })
        return mLoanPaymentsBinding.root

    }
}