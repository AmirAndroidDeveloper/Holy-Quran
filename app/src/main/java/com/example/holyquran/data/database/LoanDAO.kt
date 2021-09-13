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
    suspend fun deleteLoan(loan: Loan )

    @Query("SELECT * from loan WHERE user_id = :key")
    fun get(key: Long): LiveData<Loan>?

    @Query("SELECT * FROM loan ORDER BY amount DESC")
    fun getAllLoan(): LiveData<List<Loan>>
}