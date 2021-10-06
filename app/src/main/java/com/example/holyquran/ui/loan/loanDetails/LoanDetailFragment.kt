package com.example.holyquran.ui.loan.loanDetails

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
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
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holyquran.ui.increaseMoney.IncreaseMoneyFragmentDirections
import kotlinx.android.synthetic.main.fragment_add_user.*
import kotlinx.android.synthetic.main.fragment_loan_detail.view.*
import java.text.NumberFormat
import java.util.*

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
        val bankDAO = UserDatabase.getInstance(application).mBankDAO
        val viewModelFactory =
            ViewModelProviderFactory(userDAO, transactionDAO, loanDAO, bankDAO, application)

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
        id = arg.loanDetailId
        mLoanDetailViewModel.setLoanDetail(id)?.observe(viewLifecycleOwner, {
            mLoanDetailViewModel.setLoanDetail(it)
        })
        Log.d("TAG", "onCreateView: $id")
        mLoanDetailViewModel.loanDetail.observe(viewLifecycleOwner, {
            if (it != null) {
                val yourInputString = it.createDate.toString()
                yourInputString.split('/', limit = 3).also { (year, month, day) ->
                    Log.d(
                        "TAG",
                        "onCreateView DIVORCE $year/$month/$day"
                    )
                    val pDate = PersianDate()
                    val pdformater1 = PersianDateFormat("Y/m/d")
                    pdformater1.format(pDate)
                    mLoanDetailBinding.loanExpiredAt.text =
                        pdformater1.format(
                            pDate.setShYear(year.toInt()).addMonth(it.loanSections.toLong())
                                .setShDay(day.toInt())
                        )
                }
                mLoanDetailBinding.loanInfo = it
            }
        })


        mLoanDetailViewModel.setUserInfo(id)?.observe(viewLifecycleOwner, {
            if (it != null) {
                mLoanDetailViewModel.setUserInfo(it)
            }
        })

        mLoanDetailViewModel.userInfo.observe(viewLifecycleOwner, {
            if (it != null) {
                mLoanDetailBinding.userInfo = it
                Log.d("TAG", "onCreateView: ${it.userId}")
                mLoanDetailBinding.userLoan.text = it.fullName
            }
        })




        setHasOptionsMenu(true)
        return mLoanDetailBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.payment_list, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.loanPaymentList -> {
                this.findNavController().navigate(
                    LoanDetailFragmentDirections.actionLoanDetailFragmentToLoanPaymentsFragment(
                        id
                    )
                )
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }
}


//mLoanDetailBinding.loanExpiredAt.text = pdformater1.format(pdate.addMonth(it.loanSections.toLong()))


//        val paymentAdapter = PaymentAdapter()
//        val mLinearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//        mLoanDetailBinding.rvFeed.adapter = paymentAdapter
//        mLoanDetailBinding.rvFeed.layoutManager = mLinearLayoutManager
//        mLoanDetailViewModel.getAllLoans(id).observe(viewLifecycleOwner, {
//            Log.d("TAG", "userInfo2: ${it}")
//            mLoanDetailViewModel.loanInfo.value = it
//        })
//val pdate = PersianDate()
//val pdformater1 = PersianDateFormat("Y/m/d")
//pdformater1.format(pdate.addDate(year.toLong(), month.toLong(), day.toLong()))
//mLoanDetailBinding.loanExpiredAt.text = pdformater1.format(pdate)
