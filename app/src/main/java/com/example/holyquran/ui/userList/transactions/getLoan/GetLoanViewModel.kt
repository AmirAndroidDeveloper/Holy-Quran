package com.example.holyquran.ui.userList.transactions.getLoan

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holyquran.data.database.LoanDAO
import com.example.holyquran.data.database.TransactionsDAO
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.data.model.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GetLoanViewModel(
    val mUserInfoDAO: UserDAO,
    val mTransactionsDAO: TransactionsDAO,
    val mGetLoanDAO: LoanDAO,

    application: Application
) :
    AndroidViewModel(application) {
    var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)




//    fun insertLoanTimeSpinner(
//        name: String
//    ) {
//        var userId : Long = userList.value?.get(selectedItemPosition)?.userId!!
//        uiScope.launch {
//            mSubCategoryDAO.insert(SubCategory(0L,categoryId,name,null))
//            _insertSuccess.value=true
//        }
//    }




    private val _userList = MutableLiveData<List<UserInfo>>()
    val userList : LiveData<List<UserInfo>>
        get() = _userList

    var selectedItemPosition = 0
    fun onSelectItem(postion: Int) {
        selectedItemPosition = postion;

        var userId= userList.value?.get(postion)?.userId

        Log.d("position", "onSelectItem: $postion")
    }

}