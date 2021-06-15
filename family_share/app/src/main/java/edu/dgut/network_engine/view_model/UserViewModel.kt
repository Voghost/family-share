package edu.dgut.network_engine.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.database.room_db.FamilyShareDatabase
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.api.UserApi
import edu.dgut.network_engine.web_request.apiCall
import edu.dgut.network_engine.web_request.tdo.NewUser
import edu.dgut.network_engine.web_request.tdo.Token
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: UserDao? = null
    private lateinit var allUserList: LiveData<List<User>>

    init {
        val familyShareDatabase: FamilyShareDatabase = FamilyShareDatabase.getInstance(application)
        userDao = familyShareDatabase.getUserDao()
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
    fun getAllUserWithUserList(): LiveData<List<UserWithAccountList>>? {
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


    /**
     * 登录
     */
    suspend fun login(userName: String, password: String): BaseResponse<Token>? {
        var loginMap = mapOf<String, String>("username" to userName, "password" to password)
        val res = apiCall { UserApi.get().login(loginMap) }
        return if (res.code != 200 || res.data == null) {
            Toast.makeText(getApplication(), res.toString(), Toast.LENGTH_SHORT).show()
            null
        } else {
            res
        }
    }

    /**
     * 注册
     */
    suspend fun register(newUser: NewUser): Boolean {
        val res = apiCall { UserApi.get().register(newUser) }
        return if (res.code == 200 && res.data != null) {
            var user: User = User()
            user.userId = res.data?.userId
            user.username = res.data?.username
            user.nickname = res.data?.nickname
            user.phone = res.data?.phone
            user.primaryUser = res.data?.primaryUser
            user.createTime = res.data?.createTime
            user.updateTime = res.data?.updateTime
            user.avatarUrl = res.data?.avatarUrl
            user.familyCode = res.data?.familyCode
            println(res)
            insertUser(user)
            true;
        } else {
            false
        }
    }

    /**
     * 同步family
     */
}

