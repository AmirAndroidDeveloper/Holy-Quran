package com.example.holyquran.ui.mainPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainFragmentViewModel(
    val mUserInfoDAO: UserDAO,
   val dataSourceTransactions: TransactionsDAO,
    application: Application
) : AndroidViewModel(application) {

    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    val userInfo = MutableLiveData<List<UserInfo>>()
    fun getUserList():LiveData<List<UserInfo>> {
        return mUserInfoDAO.getAllUserInfo()
    }

    private val _userName = MutableLiveData<UserInfo>()
    val userName: LiveData<UserInfo>
        get() = _userName


    fun getUserInfo(id: Long): LiveData<UserInfo>? {
        return mUserInfoDAO.get(id)
    }
    fun setUserInfo(mUserInfo: UserInfo) {
        _userName.value = mUserInfo
    }


    private val _noNameTillKnow = MutableLiveData<Boolean>(false)
    val noNameTillKnow: LiveData<Boolean>
        get() = _noNameTillKnow


    fun noNameTillKnow() {
        _noNameTillKnow.value = true
    }


}

