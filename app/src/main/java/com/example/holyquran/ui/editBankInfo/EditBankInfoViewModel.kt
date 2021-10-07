package com.example.holyquran.ui.editBankInfo

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
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.*

class EditBankInfoViewModel(
    private val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application,
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _bankName = MutableLiveData<Bank>()
    val bankName: LiveData<Bank>
        get() = _bankName

    fun setBankName(id: Long): LiveData<Bank>? {
        return mBankDAO.get(id)
    }

    fun setBankName(mUserInfo: Bank) {
        _bankName.value = mUserInfo
    }

    private val _validInfo = MutableLiveData<Boolean>(false)
    val validInfo: LiveData<Boolean>
        get() = _validInfo

    fun validDone() {
        _validInfo.value = false
    }

    fun onSubmitBtnClick() {
        _validInfo.value = true
    }

    fun updateBank(
        bankInfo: Bank
    ) {
        uiScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    mBankDAO.update(bankInfo)
                }
                _updateSuccess.value = true

            } catch (e: Exception) {
                Log.d("TAG", "insertContact: ${e.message}")
            }
        }
    }

    private val _updateSuccess = MutableLiveData<Boolean>(false)
    val updateSuccess: LiveData<Boolean>
        get() = _updateSuccess

    }