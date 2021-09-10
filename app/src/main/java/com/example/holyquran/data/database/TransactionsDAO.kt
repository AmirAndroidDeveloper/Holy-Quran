package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo

@Dao
interface TransactionsDAO {
    @Insert
    suspend fun insert(ta: Transaction): Long

    @Insert
    suspend fun insertList(ta: List<Transaction>)

    @Update
    suspend fun update(ta: Transaction)

    @Delete
    suspend fun deleteTrans(ta: Transaction)

    @Query("SELECT * from trans WHERE trans_id = :key")
    fun get(key: Long): LiveData<Transaction>?

    @Query("SELECT * FROM trans ORDER BY increase ASC")
    fun getAllTransaction(): LiveData<List<Transaction>>

    @Query("SELECT SUM(increase)FROM trans")
    fun getTotalIncrease(): Int
}