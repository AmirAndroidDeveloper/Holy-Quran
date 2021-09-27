package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo

@Dao
interface TransactionsDAO {
    @Insert
    suspend fun insert(ta: Transaction): Long

    @Update
    suspend fun update(ta: Transaction)

    @Delete
    suspend fun deleteTrans(ta: Transaction)

    @Query("SELECT * from `transaction` WHERE trans_id = :key")
    fun get(key: Long): LiveData<Transaction>?

    @Query("SELECT SUM(loan_number) FROM `transaction` WHERE user_id=:key")
    fun sumLoanPayments(key: Long): Int

    @Query("SELECT SUM(loan_number) FROM `transaction` WHERE user_id=:key")
    fun sumWholeIncrease(key: Long): Int

    @Query("SELECT * FROM `transaction` WHERE user_id=:key ORDER BY increase ,decrease DESC")
    fun getAllTransactionByUserId(key: Long): LiveData<List<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY increase ASC")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Query("SELECT SUM(increase)FROM `transaction`")
    fun getTotalIncrease(): Int

    @Query("SELECT SUM(increase) FROM `transaction` WHERE user_id=:key")
    fun sumUserIncrease(key: Long): Int

    @Query("SELECT SUM(decrease) FROM `transaction` WHERE user_id=:key")
    fun sumUserDecrease(key: Long): Int
}