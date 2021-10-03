package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Loan

@Dao
interface BankDAO {
    @Insert
    suspend fun insert(bank: Bank): Long

    @Insert
    suspend fun insertList(bank: MutableList<Bank>)

    @Delete
    suspend fun deleteBank(bank: Bank)

    @Query("SELECT * from bank WHERE bank_id = :key")
    fun get(key: Long): LiveData<Bank>?

    @Query("SELECT * FROM bank ORDER BY bank_name DESC")
    fun getAllBanks(): LiveData<List<Bank>>


    @Query("SELECT SUM(increase) FROM `transaction` WHERE bank_id=:key")
    fun sumBankIncrease(key: Long): Int

}