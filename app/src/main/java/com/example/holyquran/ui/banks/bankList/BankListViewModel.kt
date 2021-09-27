package com.example.holyquran.ui.banks.bankList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class BankListViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _goTOAddBank = MutableLiveData<Boolean>(false)
    val goTOAddBank: LiveData<Boolean>
        get() = _goTOAddBank

    fun goTOAddBank() {
        _goTOAddBank.value = true
    }

    fun goTOAddBankDone() {
        _goTOAddBank.value = false
    }
}