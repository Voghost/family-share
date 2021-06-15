package edu.dgut.network_engine.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.dgut.network_engine.database.entity.Account

/**
 * AccountDao
 */
@Dao
interface AccountDao {


    /**
     * 获取所有数据的列表
     * @return LiveData  实时数据 不用在新的线程执行
     */
    @Query("SELECT * FROM account_table WHERE isDeleted=0")
    fun getAll(): LiveData<List<Account>>

    /**
     * 插入一条数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(account: Account)


    /**
     *通过id删除一条数据
     */
    @Query("UPDATE account_table SET isDeleted= 1 WHERE accountId = :id")
    suspend fun deleteById(id: Long)

    /**
     * 更新数据
     */
    @Update
    suspend fun update(account: Account)

}