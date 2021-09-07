package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trans_id")
    var transId: Long = 0L,
    @ColumnInfo(name = "user_id")
    var userId: String,
    @ColumnInfo(name = "amount")
    var amount: Long,
    @ColumnInfo(name = "create_date")
    var createDate: String,
    @ColumnInfo(name = "bank_id")
    var bankId: Long,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "increase")
    var increase: Long,
    @ColumnInfo(name = "decrease")
    var decrease: Long,
    @ColumnInfo(name = "loan_number")
    var loanNumber: Long
)
