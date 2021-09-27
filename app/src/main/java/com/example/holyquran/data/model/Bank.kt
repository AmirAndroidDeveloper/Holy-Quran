package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank")
data class Bank(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "bank_id")
    var bankId: Long = 0L,
    @ColumnInfo(name = "account_number")
    var accountNumber: String,
    @ColumnInfo(name = "card_number")
    var cardNumber: String,
    @ColumnInfo(name = "address")
    var address: String,
    @ColumnInfo(name = "name")
    var name: String
)