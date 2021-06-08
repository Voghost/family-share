package edu.dgut.network_engine.database.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author Edgar Liu
 *
 * 数据库创建类
 * 单例模式， 保证内存中只有一个实例
 */
@Database(entities = [User::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao

    /**
     * 数据库初始化
     */
    private class UserDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var userDao = database.getUserDao()

                    // 初始化 用户id为1
                    var user = User(
                        1L,
                        null,
                        null,
                        null,
                        null,
                        Date().time,
                        Date().time,
                        true
                    )
                    userDao.insert(user)
                }
            }
        }
    }

    /**
     * 单例模式
     */
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        val applicationScope = CoroutineScope(SupervisorJob())


        fun getInstance(context: Context): UserDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java,
                "cal_date_db"
            ).addCallback(UserDatabaseCallback(applicationScope)).build()
    }

}