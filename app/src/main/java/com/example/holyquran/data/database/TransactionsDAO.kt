package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.*
import com.example.holyquran.data.model.Transaction

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

    @Query("SELECT * from `transaction`WHERE type=:key")
    fun getAll(key: String): LiveData<Transaction>?

    @Query("SELECT SUM(loan_number) FROM `transaction` WHERE user_id=:key")
    fun sumLoanPayments(key: Long): Int

    @Query("SELECT SUM(loan_number) FROM `transaction` WHERE user_id=:key")
    fun sumWholeIncrease(key: Long): Int

    @Query("SELECT * FROM `transaction` WHERE user_id=:key ORDER BY increase ,decrease DESC")
    fun getAllTransactionByUserId(key: Long): LiveData<List<Transaction>>

    @Query("SELECT * FROM `transaction` ORDER BY increase ASC")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Query("SELECT SUM(increase) FROM `transaction` WHERE user_id=:key")
    fun sumUserIncrease(key: Long): Long

    @Query("SELECT SUM(decrease) FROM `transaction` WHERE user_id=:key")
    fun sumUserDecrease(key: Long): Long

    @Query("SELECT SUM(increase) FROM `transaction` WHERE bank_id=:key")
    fun sumBankIncrease(key: Long): Long

    @Query("SELECT SUM(decrease) FROM `transaction` WHERE bank_id=:key")
    fun sumBankDecrease(key: Long): Long

    @Query("SELECT `transaction`.increase, `transaction`.decrease, `transaction`.type ,bank.bank_name From `transaction` JOIN bank WHERE `transaction`.bank_id=:key ")
    fun joinTables(key: Long): LiveData<TransactionAndBank>?

    @Query("SELECT `transaction`.increase, `transaction`.decrease,`transaction`.type, bank.bank_name From `transaction`  JOIN bank WHERE `transaction`.user_id=:key ")
    fun joinAllTables(key: Long): LiveData<List<TransactionAndBank>>

    @Query("SELECT SUM(increase) FROM `transaction`")
    fun sumAllIncrease(): Long

    @Query("SELECT SUM(decrease) FROM `transaction`")
    fun sumAllDecrease(): Long

    @Query("SELECT SUM(increase) FROM `transaction` WHERE type=:key")
    fun sumAllUserPayments(key: String): Long

    @Query("SELECT SUM(increase) FROM `transaction` WHERE type=:key")
    fun sumAllUserDeposit(key: String): Long


//    @Query("SELECT `transaction`.trans_id FROM `transaction`  JOIN bank ")
//    fun joinTables():LiveData<TransactionAndBank>?
}