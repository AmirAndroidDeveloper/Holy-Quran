package com.example.holyquran.ui.loan.payments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.UserInfo

class PaymentViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    private val _loanDetail = MutableLiveData<Loan>()
    val loanDetail: LiveData<Loan>
        get() = _loanDetail

    fun setLoanDetail(id: Long): LiveData<Loan>? {
        return mLoan.get(id)
    }

    fun setLoanDetail(mLoanDetail: Loan) {
        _loanDetail.value = mLoanDetail
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
