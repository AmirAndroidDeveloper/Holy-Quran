package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.holyquran.data.model.Transaction

@Dao
interface TransactionsDAO {
    @Insert
    suspend fun insert(ta: Transaction) : Long

    @Insert
    suspend fun insertList(ta: List<Transaction>)

    @Update
    suspend fun update(ta: Transaction)



    @Query("SELECT * from trans WHERE trans_id = :key")
    fun get(key: Long):  LiveData<Transaction>?

    @Query("SELECT * from trans WHERE user_id = :key")
    fun getTransActionWithUserId(key: Long):  LiveData<List<Transaction>>?

    @Query("SELECT * FROM trans  ORDER BY amount DESC")
    fun getAllTransAction(): LiveData<List<Transaction>>


    @Query("SELECT * FROM trans WHERE user_id=:key ")
    fun getAllTransWithUserId(key: Long): LiveData<List<Transaction>>

}