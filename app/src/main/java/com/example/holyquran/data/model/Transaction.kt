package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "trans")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trans_id")
    var transId: Long = 0L,
    @ColumnInfo(name = "user_id")
    var userId:Long,
    @ColumnInfo(name = "amount")
    var amount: Long?,
    @ColumnInfo(name = "create_date")
    var createDate: String?,
    @ColumnInfo(name = "bank_id")
    var bankId: Long?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "increase")
    var increase: String?,
    @ColumnInfo(name = "decrease")
    var decrease: String?,
    @ColumnInfo(name = "loan_number")
    var loanNumber: Long?,
    @ColumnInfo(name = "transaction_status")
    var transactionStatus: Boolean,
    @ColumnInfo(name = "total")
    var total: Int
    )
