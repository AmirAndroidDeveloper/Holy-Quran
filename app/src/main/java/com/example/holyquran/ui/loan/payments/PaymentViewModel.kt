package com.example.holyquran.ui.loan.payments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo

class PaymentViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    private val _loanPayments = MutableLiveData<Transaction>()
    val loanPayments: LiveData<Transaction>
        get() = _loanPayments


    fun sumLoanPayments(id: Long): Int {
        return mTransactionsDAO.sumLoanPayments(id)
    }

    fun sumWholePayments(id: Long): Int {
        return mTransactionsDAO.sumWholeIncrease(id)
    }


    fun setLoanPayments(id: Long): LiveData<Transaction>? {
        return mTransactionsDAO.get(id)
    }

    fun setLoanPayments(mLoanDetail: Transaction) {
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

    val loanInfo = MutableLiveData<List<Loan>>()
    fun getLoanList(id: Long): LiveData<List<Loan>> {
        Log.d("TAG", "getLoanList: $loanInfo")
        return mLoan.getAllLoanByUserPayment(id)
    }


//    fun setLoanDetailTest(id: Long): LiveData<Loan>? {
//        return mLoan.get(id)
//    }
//
//    fun setLoanDetailTest(mLoanDetail: Loan) {
//        _loanDetail.value = mLoanDetail
//    }

}
