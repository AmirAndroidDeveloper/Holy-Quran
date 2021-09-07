package com.example.holyquran.data.model

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "increase")
data class Increase (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "increase_id")
    var increaseId:Long=0L,
    @ColumnInfo(name = "user_id")
    var userId:Long=0L,
    @ColumnInfo(name = "increase_amount")
    var increaseAmount:String



        )
