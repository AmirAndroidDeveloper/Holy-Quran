package com.example.holyquran

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.holyquran.data.database.UserDAO
import com.example.holyquran.ui.addUser.AddUserViewModel
import com.example.holyquran.ui.mainPage.MainFragmentViewModel
import com.example.holyquran.ui.userList.UserListViewModel

class ViewModelProviderFactory (
    private val dataSourceUser: UserDAO,
    private val application: Application
    ): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(dataSourceUser, application) as T
        } else if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(dataSourceUser, application) as T
        } else if (modelClass.isAssignableFrom(AddUserViewModel::class.java)) {
            return AddUserViewModel(dataSourceUser, application) as T

        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}