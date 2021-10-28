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

    @Query("SELECT * from loan WHERE user_id = :key")
    fun get(key: Long): LiveData<Loan>?

    @Query("SELECT * from loan")
    fun getAll(): LiveData<Loan>?

    @Query("SELECT * FROM loan ORDER BY amount DESC")
    fun getAllLoans(): LiveData<List<Loan>>

    @Query("SELECT * FROM loan WHERE user_id=:key ORDER BY loan_id DESC")
    fun getAllLoanWithUserID(key: Long): LiveData<List<Loan>>

    @Query("SELECT * FROM loan WHERE user_id=:key")
    fun getLoan(key: Long): LiveData<Loan>

    @Query("SELECT SUM(amount) FROM `loan`")
    fun sumAllLoans(): Long

}

