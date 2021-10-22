package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "loan")
data class Loan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "loan_id")
    var loanId: Long = 0L,
    @ColumnInfo(name = "user_id")
    var userId: Long,
    @ColumnInfo(name = "amount")
    var amount: String,
    @ColumnInfo(name = "create_date")
    var createDate: String?,
    @ColumnInfo(name = "loan_sections")
    var loanSections: String,
    @ColumnInfo(name = "expired_date")
    var expiredDate: String?,
//    @ColumnInfo(name = "section_time")
//    var sectionTime: String,
    @ColumnInfo(name = "payment")
    var payment: String
)