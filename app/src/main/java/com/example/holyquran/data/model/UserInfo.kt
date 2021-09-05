package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

//
//@Entity(tableName = "user_info")
//data class UserInfo(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "info_id")
//    var infoId:Long=0L,
//    @ColumnInfo(name = "name")
//    var name:String,
//    @ColumnInfo(name= "personal_code")
//    var personalCode:String,
//    @ColumnInfo(name = "phone_number")
//    var phoneNumber:String

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long = 0L,
    @ColumnInfo(name = "UserName")
    var UserName: String,
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String,
    @ColumnInfo(name = "personal_code")
    var personalCode: String,
    @ColumnInfo(name = "address")
    var address: String,
    )
