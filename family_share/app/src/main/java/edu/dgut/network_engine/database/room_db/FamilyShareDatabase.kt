package edu.dgut.network_engine.database.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.dgut.network_engine.database.converters.DateConverters
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.dao.MemorandumDao
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.database.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * @author Edgar Liu
 *
 * 数据库创建类
 * 单例模式， 保证内存中只有一个实例
 */
@Database(entities = [User::class, Account::class, Memorandum::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class FamilyShareDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getAccountDao(): AccountDao
    abstract fun getMemorandumDao(): MemorandumDao


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
                    /**
                     * 数据库初始化操作
                     */
                    /*  var userDao = database.getUserDao()
                      // 先删除所有用户
                      userDao.deleteAll()

                      // 初始化 用户id为1
                      var user = User(
                          1L,
                          null,
                          null,
                          null,
                          null,
                          Date().time,
                          Date().time,
                          null,
                          true,
                          1
                      )
                      userDao.insert(user)*/
                }
            }
        }
    }

    /**
     * 单例模式
     */
    companion object {
        @Volatile
        private var INSTANCE: FamilyShareDatabase? = null
        private val applicationScope = CoroutineScope(SupervisorJob())


        fun getInstance(context: Context): FamilyShareDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FamilyShareDatabase::class.java,
                "family_share_db"
            ).addCallback(UserDatabaseCallback(applicationScope)) // 加入callback
                .build()
    }

}