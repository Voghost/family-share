package edu.dgut.network_engine.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.database.entity.User


@Dao
interface MemorandumDao {

    /**
     * 全部删除
     */
    @Query("DELETE FROM memorandum_table")
    suspend fun deleteAll()

    /**
     * 批量添加
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(memorandumList: List<Memorandum>)

    /**
     * 添加一个
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memorandum: Memorandum)

    /**
     * 更新一个
     */
    @Update
    suspend fun update(memorandum: Memorandum)

    /**
     * 获取全部
     */
    @Query("SELECT * FROM memorandum_table")
    fun getAll(): LiveData<List<Memorandum>>

    /**
     * 通过id删除一个
     */
    @Query("DELETE FROM memorandum_table WHERE id = :id")
    suspend fun deleteById(id: Long)

}