package edu.dgut.network_engine.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.database.room_db.UserDatabase
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : AndroidViewModel {
    private var userDao: UserDao? = null
    private lateinit var allUserList: LiveData<List<User>>

    constructor(application: Application) : super(application) {
        val userDatabase: UserDatabase = UserDatabase.getInstance(application)
        userDao = userDatabase.getUserDao()
        allUserList = userDao!!.getAll()
    }

    /**
     * 获取所有用户, LiveData 无需再开辟新线程
     */
    fun getAllUserList(): LiveData<List<User>> {
        return allUserList
    }

    /**
     * 插入一条数据, 在新的线程中执行
     */
    fun insertUser(user: User) = viewModelScope.launch {
        userDao?.insert(user)
    }

    /**
     * 删除一条数据, 在新的线程中执行
     */
    fun deleteUser(id: Long) = viewModelScope.launch {
        userDao?.delete(id)
    }

    /**
     *更新一条数据，在新的线程中
     */
    fun updateUser(user: User) = viewModelScope.launch {
        userDao?.update(user)
    }

    /**
     * 通过id获取某个主用户的所有用户成员
     */
    fun getUserWithUserListByUserId(id: Long) = viewModelScope.launch {
        userDao?.getUsersWithUserListByUserId(id)
    }

    /**
     * 获取所有用户的所有账单信息
     */
    fun getAllUserWithUserList() : LiveData<List<UserWithAccountList>>?{
        return userDao?.getAllUserWithAccountList()
    }

    /**
     * 通过id获取某个用户的所有账单信息
     */
    fun getUserWithAccountListByUserId(id: Long): LiveData<UserWithAccountList>? {
        return userDao?.getUsersWithAccountListByUserId(id)
    }

    /**
     * 获取数据条数
     */
    suspend fun getAllCount() = withContext(viewModelScope.coroutineContext) {
        userDao?.getAllCount()
    }


}