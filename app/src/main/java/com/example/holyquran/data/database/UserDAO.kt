package com.example.holyquran.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.holyquran.data.model.UserInfo

@Dao
interface UserDAO {

@Insert
suspend fun insert(ui:UserInfo):Long

@Insert
suspend fun insertList(category: MutableList<UserInfo>)

    @Query("SELECT * from user_info WHERE user_id = :key")
    fun get(key: Long):  LiveData<UserInfo>?

@Query("SELECT * FROM user_info ORDER BY UserName ASC")
fun getAllUserInfo(): LiveData<List<UserInfo>>
}