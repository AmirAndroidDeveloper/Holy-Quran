package com.example.holyquran.data.model

import androidx.room.ColumnInfo

data class LoanAndUserInfo(
    @ColumnInfo(name = "user_id")
    var userId: Long =0L,
    @ColumnInfo(name = "increase")
    var increase: String,
    @ColumnInfo(name = "type")
    var type: String,
)
