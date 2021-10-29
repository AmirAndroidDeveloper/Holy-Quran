package com.example.holyquran.ui.addUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.BankDAO
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.*
import kotlinx.coroutines.*
import androidx.databinding.ObservableField

class AddUserViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionDAO: TransactionsDAO,
    val mLoan: LoanDAO,
    val mBankDAO: BankDAO,
    application: Application
) :
    AndroidViewModel(application) {
    var amount = ""
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Default + viewModelJob)

    fun insertUser(
        fullName: String,
        accountId: String,
        mobileNumber: String,
        phoneNumber: String,
        dateOfCreation: String,
        address: String,
    ) {
        uiScope.launch {
            try {
                Log.d("TAG", "insertTest")
                val user = UserInfo(
                    0L,
                    fullName,
                    accountId,
                    mobileNumber,
                    phoneNumber,
                    dateOfCreation,
                    address
                )
                withContext(Dispatchers.IO) {
                    val id = mUserInfoDAO.insert(user)
                    Log.d("TAG", "insertUser: $amount")
                    val increasePage = "firstMoney"
                    val bankId: Long = bankInfo.value?.get(selectedItemPosition)?.bankId!!
                    mTransactionDAO.insert(
                        Transaction(
                            0L,
                            id,
                            null,
                            bankId,
                            null,
                            amount,
                            null,
                            null,
                            null,
                            increasePage
                        )
                    )
                }

            } catch (e: Exception) {
                Log.d("TAG", "insertContact: ${e.message}")
            }
        }
    }

    private var username = ObservableField("").toString()
    fun afterUserNameChange(s: CharSequence) {
        val removeComma =
            NumberTextWatcherForThousand.trimCommaOfString(s.toString())
                .replace(",", "")
        amount = removeComma
        this.username = removeComma
    }


    private val _openCalender = MutableLiveData<Boolean>(false)
    val openCalender: LiveData<Boolean>
        get() = _openCalender

    fun openCalender() {
        _openCalender.value = true
    }

    fun openCalenderDone() {
        _openCalender.value = false
    }


    private val _addUser = MutableLiveData<Boolean>(false)
    val addUser: LiveData<Boolean>
        get() = _addUser

    fun addUser() {
        _addUser.value = true
    }

    private val _userName = MutableLiveData<UserInfo>()
    val userName: LiveData<UserInfo>
        get() = _userName

    fun setUserName(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }

    fun setUserName(mUser: UserInfo) {
        _userName.value = mUser
    }

    var selectedItemPosition = 0
    fun onSelectItem(postion: Int) {
        selectedItemPosition = postion;
    }

    val bankInfo = MutableLiveData<List<Bank>>()
    fun getBankList(): LiveData<List<Bank>> {
        return mBankDAO.getAllBanks()
    }

}
