package com.example.holyquran.data.model

import androidx.room.ColumnInfo

data class TransactionAndBank(
    @ColumnInfo(name = "increase")
    var increase: String?,
    @ColumnInfo(name = "decrease")
    var decrease: String?,
    @ColumnInfo(name = "type")
    var type: String?,
    @ColumnInfo(name = "bank_name")
    var bankName: String,
)