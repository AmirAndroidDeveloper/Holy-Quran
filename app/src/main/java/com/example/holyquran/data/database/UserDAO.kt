package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.TransactionAndBank
import com.example.holyquran.data.model.UserAndMoney
import com.example.holyquran.data.model.UserInfo

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(ui: UserInfo): Long

    @Update
    suspend fun update(ui: UserInfo)

    @Insert
    suspend fun insertList(ui: MutableList<UserInfo>)

    @Delete
    suspend fun deleteUser(ui: UserInfo)

    @Query("DELETE FROM user_info")
    fun deleteAllUser()

    @Query("SELECT user_info.user_id, `user_info`.full_name, `transaction`.total From user_info JOIN `transaction` ")
    fun joinTable(): LiveData<List<UserAndMoney>>?

    @Query("SELECT * from user_info WHERE user_id = :key")
    fun get(key: Long): LiveData<UserInfo>?

    @Query("SELECT * FROM user_info ORDER BY full_name DESC")
    fun getAllUserInfo(): LiveData<List<UserInfo>>

    @Query("SELECT * FROM user_info where full_name like '%' || :fullName || '%' ORDER BY full_name ASC")
    fun searchUserName(fullName: String): LiveData<List<UserInfo>>


}