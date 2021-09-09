package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Long =0L,
    @ColumnInfo(name = "full_name")
    var fullName:String?,
    @ColumnInfo(name= "account_id")
    var accountId: String?,
    @ColumnInfo(name = "mobile_number")
    var mobileNumber:String?,
    @ColumnInfo(name = "phone_number")
    var phoneNumber:String?,
    @ColumnInfo(name = "date_of_creation")
    var dateOfCreation:String?,
    @ColumnInfo(name = "address")
    var Address:String?)

