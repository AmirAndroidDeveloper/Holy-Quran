package com.example.holyquran.ui.userList.transactions.loan.loanDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.holyquran.R
import com.example.holyquran.databinding.FragmentLoanDetailBinding

class LoanDetailFragment : Fragment() {
    lateinit var mLoanDetailBinding: FragmentLoanDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mLoanDetailBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_loan_detail, container, false)
        return mLoanDetailBinding.root
    }
}