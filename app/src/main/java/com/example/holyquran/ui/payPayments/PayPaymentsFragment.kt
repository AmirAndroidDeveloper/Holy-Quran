package com.example.holyquran.ui.payPayments

import NumberTextWatcherForThousand
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentPayPaymentsBinding
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.ArrayList

class PayPaymentsFragment : Fragment() {
    var userId = 0L
    var descide = ""
    lateinit var mPayPaymentsBinding: FragmentPayPaymentsBinding
    lateinit var mPaymentsViewModel: PayPaymentsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPayPaymentsBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.fragment_pay_payments,
                container,
                false
            )

        val application = requireNotNull(this.activity).application
        val userDAO = UserDatabase.getInstance(application).mUserDAO
        val transactionDAO = UserDatabase.getInstance(application).mTransactionsDAO
        val loanDAO = UserDatabase.getInstance(application).mLoanDAO
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, bankDAO, application)
        mPaymentsViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(PayPaymentsViewModel::class.java)
        mPayPaymentsBinding.viewModel = mPaymentsViewModel
        this.also { mPayPaymentsBinding.lifecycleOwner = it }

        val arg =
            PayPaymentsFragmentArgs.fromBundle(
                requireArguments()
            )
        userId = arg.id

        mPaymentsViewModel.setUserName(userId)?.observe(viewLifecycleOwner, {
            mPaymentsViewModel.setUserName(it)
        })
        mPaymentsViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mPayPaymentsBinding.userName = it
            }
        })

        mPaymentsViewModel.setLoan(userId)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mPaymentsViewModel.setLoan(it)
            } else {
                notShowLoanInfo()
            }
        })
        mPaymentsViewModel.loan.observe(viewLifecycleOwner, {
            if (it != null) {
                val formatter: NumberFormat = DecimalFormat("#,###,###,###")
                mPayPaymentsBinding.paymentPrice.setText("" + formatter.format(it.payment.toLong()))
                mPayPaymentsBinding.loanAmount.setText("" + formatter.format(it.amount.toLong()))
                mPayPaymentsBinding.loan = it
            }
        })
        mPayPaymentsBinding.paymentsMoneyEditText.addTextChangedListener(
            NumberTextWatcherForThousand(
                mPayPaymentsBinding.paymentsMoneyEditText
            )
        )
        mPayPaymentsBinding.submit.setOnClickListener {

            mPaymentsViewModel.loan.observe(viewLifecycleOwner, {
                if (it != null) {
                    val removeComma =
                        NumberTextWatcherForThousand.trimCommaOfString(mPayPaymentsBinding.paymentsMoneyEditText.text.toString())
                    val currentPayment = it.payment
                    if (removeComma==currentPayment){
                        val payPayment = "payPayment"
                                mPaymentsViewModel.insertMoney(
                                    removeComma,
                                    userId,
                                    payPayment
                                )
                    }else{
                        val builder: AlertDialog.Builder =
                            AlertDialog.Builder(requireActivity())
                        builder.setIcon(R.drawable.warning)
                        mPaymentsViewModel.loan.observe(viewLifecycleOwner, {
                            val removeComma =
                                NumberTextWatcherForThousand.trimCommaOfString(mPayPaymentsBinding.paymentsMoneyEditText.text.toString())
                                    .replace(",", "")
                            if (it != null) {
                                mPayPaymentsBinding.loan = it
                                val currentPayment = it.payment
                                if (removeComma.toLong() > currentPayment.toLong()) {
                                    val more = "بیشتر"
                                    descide = more
                                } else if (removeComma.toLong() < currentPayment.toLong()) {
                                    val less = "کمتر"
                                    descide = less
                                }
                                builder.setTitle(" مبلغ مورد نظر از مبلغ قسط وام $descide است. ادامه میدهید؟")
                                    .setCancelable(false)
                                    .setPositiveButton("اره به هر حال واریز کن",
                                        DialogInterface.OnClickListener { dialog, id ->
                                            val payPayment = "payPayment"
                                            mPaymentsViewModel.insertMoney(
                                                removeComma,
                                                userId,
                                                payPayment
                                            )
                                            Toast.makeText(
                                                activity,
                                                "قسط با موفقیت برداخت شد.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        })
                                    .setNegativeButton("نه,ممنون",
                                        DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() }
                                    )
                                val alert: AlertDialog = builder.create()
                                alert.setCanceledOnTouchOutside(true)
                                alert.show()
                            }
                        })
                    }
                    mPayPaymentsBinding.loan = it
                }
            })
        }
        mPaymentsViewModel.getBankList().observe(viewLifecycleOwner, {
            mPaymentsViewModel.bankInfo.value = it
            Log.d("TAG", "viewHolder: $it")

            val bankList: MutableList<String> = ArrayList() //this is list<string>
            it.forEach { item ->
                // here item is item of list category
                bankList.add(item.bankName)
            }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item, bankList
            )
            Log.d("TAG", "toBank: $bankList")
            mPayPaymentsBinding.chooseBank.adapter = adapter

        })
        mPaymentsViewModel.copyNumber.observe(viewLifecycleOwner, {
            if (it == true) {
                val getPaymentPrice = mPayPaymentsBinding.paymentPrice.text.toString()
                mPayPaymentsBinding.paymentsLayout.getEditText()?.setText(getPaymentPrice);
            }
        })

        return mPayPaymentsBinding.root
    }

    private fun notShowLoanInfo() {
        Toast.makeText(activity, "noLoanHasBEENSAVED", Toast.LENGTH_SHORT).show()
    }
}