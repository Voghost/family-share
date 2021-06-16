package edu.dgut.network_engine.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.database.entity.UserWithUserList

/**
 * @author Edgar Liu
 * user dao (user 实体的数据库操作)
 */
@Dao
interface UserDao {
    /**
     * 插入一条数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    /**
     * 删除通过id删除一条数据
     */
    @Query("DELETE FROM user_table WHERE userId = :id")
    suspend fun delete(id: Long)

    /**
     * 获取所有数据
     */
    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<User>>

    /**
     * 通过家庭主成员
     * 获取一个家庭所有的成员
     */
    @Transaction    // 原子操作(需要查询数据库两次)
    @Query("SELECT * FROM user_table WHERE userId = :id")
    suspend fun getUsersWithUserListByUserId(id: Long): UserWithUserList

    /**
     * 通过用户id
     * 查询用户的所有account数据
     */
    @Transaction
    @Query("SELECT * FROM user_table  WHERE userId = :id ")
    fun getUsersWithAccountListByUserId(id: Long): LiveData<UserWithAccountList>

    /**
     * 查询所有用户的所有account数据
     */
    @Transaction
    @Query("SELECT * FROM user_table")
    fun getAllUserWithAccountList(): LiveData<List<UserWithAccountList>>

    /**
     * 更新数据
     */
    @Update
    suspend fun update(user: User)

    /**
     * 查询用户个数
     */
    @Query("SELECT COUNT(*) FROM user_table")
    suspend fun getAllCount(): Long

    /**
     * 删除所有用户
     */
    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}