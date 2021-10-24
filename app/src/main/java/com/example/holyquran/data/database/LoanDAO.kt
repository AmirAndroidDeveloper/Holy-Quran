package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.LoanAndUserInfo
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.TransactionAndBank
import com.example.holyquran.data.model.UserInfo

@Dao
interface LoanDAO {
    @Insert
    suspend fun insert(loan: Loan): Long

    @Delete
     fun deleteLoan(loan: Loan)

    @Query("SELECT * from loan WHERE loan_id = :key")
    fun get(key: Long): LiveData<Loan>?

    @Query("SELECT * FROM loan ORDER BY amount DESC")
    fun getAllLoans(): LiveData<List<Loan>>

    @Query("SELECT * FROM loan WHERE user_id=:key ORDER BY loan_id DESC")
    fun getAllLoanWithUserID(key: Long): LiveData<List<Loan>>

    @Query("SELECT * FROM loan WHERE user_id=:key")
    fun getLoan(key: Long): LiveData<Loan>

    @Query("SELECT `loan`.amount, user_info.full_name From `user_info` JOIN loan WHERE `loan`.user_id=:key ")
    fun joinTables(key: Long): LiveData<LoanAndUserInfo>?

    @Query("SELECT `transaction`.increase, `transaction`.decrease,`transaction`.type, bank.bank_name From `transaction` JOIN bank WHERE `transaction`.bank_id=:key ")
    fun joinAllTables(key: Long): LiveData<List<TransactionAndBank>>
}

