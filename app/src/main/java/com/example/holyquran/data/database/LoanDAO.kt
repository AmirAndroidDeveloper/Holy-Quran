package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.UserInfo

@Dao
interface LoanDAO {
    @Insert
    suspend fun insert(loan: Loan): Long

    @Insert
    suspend fun insertList(loan: MutableList<Loan>)

    @Delete
     fun deleteLoan(loan: Loan)

    @Query("SELECT * from loan WHERE loan_id = :key")
    fun get(key: Long): LiveData<Loan>?

    @Query("SELECT * FROM loan ORDER BY amount DESC")
    fun getAllLoans(): LiveData<List<Loan>>

    @Query("SELECT * FROM loan WHERE user_id=:key ORDER BY loan_id DESC")
    fun getAllLoanWithUserID(key: Long): LiveData<List<Loan>>

    @Query("SELECT * from loan WHERE loan_id = :key")
    fun getLoanId(key: Long): LiveData<Loan>?

    @Query("SELECT * FROM loan WHERE user_id=:key ORDER BY amount DESC")
    fun getAllLoan(key: Long): LiveData<List<Loan>>

    @Query("SELECT * FROM loan WHERE user_id=:key ORDER BY payment DESC")
    fun getAllLoanByUserPayment(key: Long): LiveData<List<Loan>>

}