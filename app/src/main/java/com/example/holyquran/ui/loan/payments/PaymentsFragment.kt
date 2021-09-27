package com.example.holyquran.ui.loan.payments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentLoanPaymentsBinding
import java.text.NumberFormat


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
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(personalDAO, transactionDAO, loanDAO,bankDAO, application)
        mPaymentsViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(PaymentViewModel::class.java)
        mLoanPaymentsBinding.viewModel = mPaymentsViewModel
        this.also { mLoanPaymentsBinding.lifecycleOwner = it }
        val args = PaymentsFragmentArgs.fromBundle(requireArguments())
        id = args.paymentId
        val payedPayments = mPaymentsViewModel.sumLoanPayments(id).toLong()
        mLoanPaymentsBinding.payedPayments.text = payedPayments.toString()
        mPaymentsViewModel.setLoanPayments(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mLoanPaymentsBinding.transaction = it
            }
        })
        mPaymentsViewModel.loanPayments.observe(viewLifecycleOwner, {
            if (it != null) {
                mPaymentsViewModel.setLoanPayments(it)
            }
        })
        val wholeIncreaseMoney = mPaymentsViewModel.sumWholePayments(id).toString()
        mLoanPaymentsBinding.payedAmount.text =
            NumberFormat.getInstance().format(wholeIncreaseMoney.toLong())

//        calculateData()


        mPaymentsViewModel.setWholeLoan(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                val wholeAmount: String = it.amount
                val removeCommaOfWholeLoan = wholeAmount.replace(",", "")
                val convertWholeAmount = removeCommaOfWholeLoan.toLong()
                val payedAmount: String = mLoanPaymentsBinding.payedAmount.text.toString()
                val removeCommaOfWholeIncrease = payedAmount.replace(",", "")
                val convertPayedAmount = removeCommaOfWholeIncrease.toLong()
                val amountLeft: Long = convertWholeAmount - convertPayedAmount
                mLoanPaymentsBinding.amountLeft.text = NumberFormat.getInstance().format(amountLeft)
                mLoanPaymentsBinding.loan = it

                var wholePayments = it.loanSections
                val convertWholePayments = wholePayments.toLong()
                val payedPayments = mLoanPaymentsBinding.payedPayments.text.toString()
                val convertPayedPayments = payedPayments.toLong()
                val sectionsLeft= convertWholePayments-convertPayedPayments
                mLoanPaymentsBinding.paymentsLeft.text=sectionsLeft.toString()
            }
        })
        mPaymentsViewModel.wholeLoan.observe(viewLifecycleOwner, {
            if (it != null) {
                mPaymentsViewModel.setWholeLoan(it)
            }
        })


        return mLoanPaymentsBinding.root
    }
}