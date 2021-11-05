package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.*
import com.example.holyquran.data.model.Transactions

@Dao
interface TransactionsDAO {
    @Insert
    suspend fun insert(ta: Transactions): Long

    @Update
    suspend fun update(ta: Transactions)

    @Delete
    suspend fun deleteTrans(ta: Transactions)

    @Query("SELECT * from transactions WHERE trans_id = :key")
    fun get(key: Long): LiveData<Transactions>?

    @Query("SELECT * from transactions WHERE type=:key")
    fun getAll(key: String): LiveData<Transactions>?

    @Query("SELECT SUM(loan_number) FROM transactions WHERE user_id=:key")
    fun sumLoanPayments(key: Long): Int

    @Query("SELECT SUM(loan_number) FROM transactions WHERE user_id=:key")
    fun sumWholeIncrease(key: Long): Int

    @Query("SELECT * FROM transactions WHERE user_id=:key ORDER BY increase ,decrease DESC")
    fun getAllTransactionByUserId(key: Long): LiveData<List<Transactions>>

    @Query("SELECT * FROM transactions ORDER BY increase ASC")
    fun getAllTransaction(): LiveData<List<Transactions>>

    @Query("SELECT SUM(increase) FROM transactions WHERE user_id=:key")
    fun sumUserIncrease(key: Long): Long

    @Query("SELECT SUM(decrease) FROM transactions WHERE user_id=:key")
    fun sumUserDecrease(key: Long): Long

    @Query("SELECT SUM(increase) FROM transactions WHERE bank_id=:key")
    fun sumBankIncrease(key: Long): Long

    @Query("SELECT SUM(decrease) FROM transactions WHERE bank_id=:key")
    fun sumBankDecrease(key: Long): Long

    @Query("SELECT transactions.increase, transactions.decrease, transactions.type ,bank.bank_name From transactions JOIN bank WHERE transactions.bank_id=:key ")
    fun joinTables(key: Long): LiveData<TransactionAndBank>?

    @Query("SELECT transactions.increase, transactions.decrease,transactions.type, bank.bank_name From transactions  JOIN bank WHERE transactions.user_id=:key ")
    fun joinAllTables(key: Long): LiveData<List<TransactionAndBank>>

    @Query("SELECT SUM(increase) FROM transactions")
    fun sumAllIncrease(): Long

    @Query("SELECT SUM(decrease) FROM transactions")
    fun sumAllDecrease(): Long

    @Query("SELECT SUM(increase) FROM transactions WHERE type=:key")
    fun sumAllUserPayments(key: String): Long

    @Query("SELECT SUM(increase) FROM transactions WHERE type=:key")
    fun sumAllUserDeposit(key: String): Long


//    @Query("SELECT `transaction`.trans_id FROM `transaction`  JOIN bank ")
//    fun joinTables():LiveData<TransactionAndBank>?
}