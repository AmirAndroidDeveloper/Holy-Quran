package com.example.holyquran.ui.banks.addBank

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
import kotlinx.coroutines.*

class AddBankViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun insertBank(
        bankName: String,
        accountNumber: String,
        cardNumber: String,
        address: String,
        createData: String,
    ) {
        uiScope.launch {
            try {
                val bank = Bank(0L, accountNumber, cardNumber, address, bankName, createData)
                withContext(Dispatchers.IO) {
                    mBankDAO.insert(bank)
                }
            } catch (e: Exception) {
                Log.d("TAG", "insertContact: ${e.message}")
            }
        }
    }

    private val _addCard = MutableLiveData<Boolean>(false)
    val addCard: LiveData<Boolean>
        get() = _addCard

    fun addCard() {
        _addCard.value = true
    }

    private val _openCalender = MutableLiveData<Boolean>(false)
    val openCalender: LiveData<Boolean>
        get() = _openCalender

    fun openCalender() {
        _openCalender.value = true
    }


}
