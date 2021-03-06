package com.example.holyquran.ui.banks.bankDetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Transactions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BankDetailViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    var type1: String = ""
    private val _bankName = MutableLiveData<Bank>()
    val bankName: LiveData<Bank>
        get() = _bankName

    fun setBankName(id: Long): LiveData<Bank>? {
        return mBankDAO.get(id)
    }

    fun setBankName(mBankInfo: Bank) {
        _bankName.value = mBankInfo
    }

    val bankInfo = MutableLiveData<List<Bank>>()
    fun getBankList(): LiveData<List<Bank>> {
        return mBankDAO.getAllBanks()
    }

    fun sumBankMoneyIncrease(id: Long): Long {
        return mTransactionsDAO.sumBankIncrease(id)
    }

    fun sumBankMoneyDecrease(id: Long): Long {
        return mTransactionsDAO.sumBankDecrease(id)
    }

    private val _transferMoney = MutableLiveData<Boolean>(false)
    val transferMoney: LiveData<Boolean>
        get() = _transferMoney

    fun transferMoney() {
        _transferMoney.value = true
    }

    var selectedItemPosition = 0
    fun onSelectItem(postion: Int) {
        selectedItemPosition = postion;
        Log.d("position", "onSelectItem: $postion")
        type1 = "decrease"
    }

    var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    fun transferMoneyIncrease(
        amount: String,
        bankIdNo: Long?,
        transferPage: String,
    ) {
        var bankId: Long = bankInfo.value?.get(selectedItemPosition)?.bankId!!
        uiScope.launch {
            mTransactionsDAO.insert(
                Transactions(
                    0L,
                    null,
                    null,
                    bankId,
                    null,
                    amount,
                    null,
                    null,
                    null,
                    transferPage,
                )
            )

        }
    }

    fun transferMoneyDecrease(
        amount: String,
        bankId: Long,
        transferPage: String,
    ) {
        uiScope.launch {
            mTransactionsDAO.insert(
                Transactions(
                    0L,
                    null,
                    null,
                    bankId,
                    null,
                    null,
                    amount,
                    null,
                    null,
                    transferPage,
                )
            )

        }
    }
}