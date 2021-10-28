package com.example.holyquran.ui.mainPage

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.holyquran.R
import com.example.holyquran.ViewModelProviderFactory
import com.example.holyquran.data.database.UserDatabase
import com.example.holyquran.databinding.FragmentMainPageBinding
import java.text.NumberFormat


class MainPageFragment : Fragment() {
    var id: Long = 0L
    val type: String = "payPayment"
    var paidMoney: String = ""
    val number:Long = 1
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

            val resultDeletedUsers = it.size.toString().toLong()
                .minus(mMainPageBinding.deletedUsers.text.toString().toLong())
            mMainPageBinding.resultUsers.text = resultDeletedUsers.toString()
            Log.d("TAG", "onCreateView: $resultDeletedUsers")
        })
        mMainViewModel.getLoanList().observe(viewLifecycleOwner, {
            mMainViewModel.loan.value = it
            mMainPageBinding.loans.text = it.size.toString()
        })

        val sumAll = mMainViewModel.sumAllIncrease() - mMainViewModel.sumAllDecrease()
        mMainPageBinding.wholeMoney.text = NumberFormat.getInstance().format(sumAll)
        mMainPageBinding.wholeMoney.append(" ریال")

        val sumAllLoans = mMainViewModel.sumAllLoansAmount()
        mMainPageBinding.allLoans.text = NumberFormat.getInstance().format(sumAllLoans)
        mMainPageBinding.allLoans.append(" ریال")


        val sumPayments = mMainViewModel.sumUserPayments(type)
        Log.d("TAG", "test:SumWithKStringKey$sumPayments")
        mMainPageBinding.paidMoneyLoan.text = sumPayments.toString()
        paidMoney = sumPayments.toString()
        mMainPageBinding.paidMoneyLoan.append(" ریال")

        mMainViewModel.setIncrease()?.observe(viewLifecycleOwner, {
            if (it != null) {
                mMainViewModel.setIncrease(it)
                val amountLeft = mMainViewModel.sumAllLoansAmount().minus(sumPayments)
                mMainPageBinding.amountLoanLeft.text = amountLeft.toString()


                val before = mMainPageBinding.paidLoans.text.toString().toLong()
                var sumAllLoans= mMainViewModel.sumAllLoansAmount().toString()
                if (paidMoney == sumAllLoans) {
                    Toast.makeText(activity, "fix", Toast.LENGTH_SHORT).show()
                   mMainPageBinding.paidLoans.text= (before + number).toString()
                }
            }
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
