package edu.dgut.network_engine.view_model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.room_db.UserDatabase
import kotlinx.coroutines.launch

class UserViewModel : ViewModel {
    private var userDao: UserDao? = null
    private lateinit var allUserList: LiveData<List<User>>

    constructor(application: Application) : super() {
        val userDatabase: UserDatabase = UserDatabase.getInstance(application)
        userDao = userDatabase.getUserDao()
        allUserList = userDao!!.getAll()
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
}