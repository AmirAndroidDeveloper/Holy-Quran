package com.example.holyquran.data.database

import android.content.Context
import android.graphics.Typeface.createFromFile
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.holyquran.data.model.Bank
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transactions
import com.example.holyquran.data.model.UserInfo
import java.io.File

@Database(
    entities = [UserInfo::class, Transactions::class, Loan::class,Bank::class],
    version = 3,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract val mUserDAO: UserDAO
    abstract val mTransactionsDAO: TransactionsDAO
    abstract val mLoanDAO: LoanDAO
    abstract val mBankDAO: BankDAO

    companion object {
        @Volatile
        var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database")

                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                    .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}