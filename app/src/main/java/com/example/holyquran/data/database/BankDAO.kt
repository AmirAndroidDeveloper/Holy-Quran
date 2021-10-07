package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.UserInfo

@Dao
interface BankDAO {
    @Insert
    suspend fun insert(bank: Bank): Long

    @Insert
    suspend fun insertList(bank: MutableList<Bank>)

    @Update
    suspend fun update(bank: Bank)

    @Delete
    suspend fun deleteBank(bank: Bank)

    @Query("SELECT * from bank WHERE bank_id = :key")
    fun get(key: Long): LiveData<Bank>?

    @Query("SELECT * FROM bank ORDER BY bank_name DESC")
    fun getAllBanks(): LiveData<List<Bank>>


}