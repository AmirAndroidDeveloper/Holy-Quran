package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.holyquran.data.model.UserInfo

@Dao
interface UserDAO {

    @Insert
    suspend fun insert(ui: UserInfo): Long

    @Update
    suspend fun update(ui: UserInfo)

    @Insert
    suspend fun insertList(ui: MutableList<UserInfo>)

    @Delete
    suspend fun deleteCategory(ui: UserInfo )

    @Query("SELECT * from user_info WHERE user_id = :key")
    fun get(key: Long): LiveData<UserInfo>?

    @Query("SELECT * FROM user_info ORDER BY full_name DESC")
    fun getAllUserInfo(): LiveData<List<UserInfo>>
}