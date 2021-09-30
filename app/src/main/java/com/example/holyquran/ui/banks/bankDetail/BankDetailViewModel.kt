package com.example.holyquran.ui.banks.bankDetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.UserInfo

class BankDetailViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    private val _bankName = MutableLiveData<Bank>()
    val bankName: LiveData<Bank>
        get() = _bankName

    fun setBankName(id: Long): LiveData<Bank>? {
        return mBankDAO.get(id)
    }

    fun setBankName(mBankInfo: Bank) {
        _bankName.value = mBankInfo
    }
}