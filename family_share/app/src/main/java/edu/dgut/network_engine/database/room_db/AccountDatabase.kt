package edu.dgut.network_engine.database.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.entity.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Database(entities = [Account::class], version = 1)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDao

    /**
     * 单例模式
     */
    companion object {
        @Volatile
        private var INSTANCE: AccountDatabase? = null
        val applicationScope = CoroutineScope(SupervisorJob())


        fun getInstance(context: Context): AccountDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AccountDatabase::class.java,
                "account_db"
            ).build()
    }
}