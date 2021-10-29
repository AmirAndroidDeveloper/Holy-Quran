package com.example.holyquran.data.model

import androidx.room.ColumnInfo

class UserAndMoney(
    @ColumnInfo(name = "user_id")
    var userId: Long,
    @ColumnInfo(name = "user_name")
    var userName: String?,
    @ColumnInfo(name = "total")
    var total: String?,
)