package com.example.holyquran.ui.loan.payments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transactions

class PaymentViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    private val _loanPayments = MutableLiveData<Transactions>()
    val loanPayments: LiveData<Transactions>
        get() = _loanPayments


    fun sumLoanPayments(id: Long): Int {
        return mTransactionsDAO.sumLoanPayments(id)
    }

    fun sumWholePayments(id: Long): Int {
        return mTransactionsDAO.sumWholeIncrease(id)
    }


    fun setLoanPayments(id: Long): LiveData<Transactions>? {
        return mTransactionsDAO.get(id)
    }

    fun setLoanPayments(mLoanDetail: Transactions) {
        _loanPayments.value = mLoanDetail
    }

    private val _wholeLoan = MutableLiveData<Loan>()
    val wholeLoan: LiveData<Loan>
        get() = _wholeLoan

    fun setWholeLoan(id: Long): LiveData<Loan>? {
        return mLoan.get(id)
    }

    fun setWholeLoan(mWholeLoan: Loan) {
        _wholeLoan.value = mWholeLoan
    }



//    fun setLoanDetailTest(id: Long): LiveData<Loan>? {
//        return mLoan.get(id)
//    }
//
//    fun setLoanDetailTest(mLoanDetail: Loan) {
//        _loanDetail.value = mLoanDetail
//    }

}
