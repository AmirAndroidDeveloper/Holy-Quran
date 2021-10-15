package com.example.holyquran.ui.payPayments

import NumberTextWatcherForThousand
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
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
import com.example.holyquran.databinding.FragmentPayPaymentsBinding

class PayPaymentsFragment : Fragment() {
    var id = 0L
   var descide =""
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
        id = arg.id

        mPaymentsViewModel.setUserName(id)?.observe(viewLifecycleOwner, {
            mPaymentsViewModel.setUserName(it)
        })
        mPaymentsViewModel.userName.observe(viewLifecycleOwner, {
            if (it != null) {
                mPayPaymentsBinding.userName = it
            }
        })

        mPaymentsViewModel.setLoan(id)?.observe(viewLifecycleOwner, {
            if (it !=null) {
                mPaymentsViewModel.setLoan(it)
            }else{
              notShowLoanInfo()
            }
            })
        mPaymentsViewModel.loan.observe(viewLifecycleOwner, {
            if (it != null) {
                mPayPaymentsBinding.loan = it
            }
        })
        mPayPaymentsBinding.paymentsMoneyEditText.addTextChangedListener(
            NumberTextWatcherForThousand(
                mPayPaymentsBinding.paymentsMoneyEditText
            )
        )

       mPayPaymentsBinding.submit.setOnClickListener {
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

                   if (removeComma.toInt() > currentPayment.toInt()) {
                       val more = "بیشتر"
                       descide = more
                   } else {
                       val less = "کمتر"
                       descide = less
                   }
                 if (removeComma.toInt()==currentPayment.toInt()){
//                    mPaymentsViewModel.insertLoanPayments(
//                        removeComma,
//                        true,
//                        userId,
//                        increasePage
//                    )
                 }
                   builder.setTitle(" مبلغ مورد نظر از مبلغ قسط وام $descide است. ادامه میدهید؟")
                       .setCancelable(false)
                       .setPositiveButton("اره به هر حال واریز کن",
                           DialogInterface.OnClickListener { dialog, id ->
//                    mIncreaseMoneyViewModel.insertLoanPayments(
//                        removeComma,
//                        true,
//                        userId,
//                        increasePage
//                    )
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
//        mIncreaseMoneyViewModel.goToIncreaseDone()
               }
           })
       }



        return mPayPaymentsBinding.root
    }
    private fun notShowLoanInfo() {
        Toast.makeText(activity, "noLoanHasBEENSAVED", Toast.LENGTH_SHORT).show()
    }
}