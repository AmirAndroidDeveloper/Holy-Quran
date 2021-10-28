package com.example.holyquran.data.model

import androidx.room.ColumnInfo

data class LoanAndUserInfo(
    @ColumnInfo(name = "user_id")
    var userId: Long =0L,
    @ColumnInfo(name = "full_name")
    var fullName:String?,
    @ColumnInfo(name = "amount")
    var amount: String,
)