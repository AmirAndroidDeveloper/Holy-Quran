package com.example.holyquran.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.holyquran.data.model.Loan
import com.example.holyquran.data.model.Transaction
import com.example.holyquran.data.model.UserInfo

@Database(
    entities = [UserInfo::class, Transaction::class, Loan::class],
    version = 2,
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract val mUserDAO: UserDAO
    abstract val mTransactionsDAO: TransactionsDAO
    abstract val mLoanDAO: LoanDAO

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    )
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