package edu.dgut.network_engine.view_model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.graphics.translationMatrix
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
import edu.dgut.network_engine.web_request.tdo.NewUserTdo
import edu.dgut.network_engine.web_request.tdo.TokenTdo
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notifyAll

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
     * 获取自己的账户
     */
    suspend fun getMe(): User? = withContext(viewModelScope.coroutineContext) {
        userDao?.getMe()
    }

    /**
     * 获取所有用户 (非livedata)
     */
    suspend fun getAllUserListNotLiveData(): List<User>? =
        withContext(viewModelScope.coroutineContext) {
            userDao?.getAllNotLiveData()
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
        user.version = user.version?.plus(1L) //更新版本
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
        var usersWithAccountListByUserId = userDao?.getUsersWithAccountListByUserId(id)
        return usersWithAccountListByUserId
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
    suspend fun login(userName: String, password: String): Boolean {
        var loginMap = mapOf<String, String>("username" to userName, "password" to password)
        val res = apiCall { UserApi.get().login(loginMap) }
        return if (res.code != 200 || res.data == null) {
            Toast.makeText(getApplication(), res.toString(), Toast.LENGTH_SHORT).show()
            false
        } else {
            // 保存token 到sharedPreferences
            val sharedPreferences: SharedPreferences =
                getApplication<Application>()
                    .applicationContext
                    .getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", res?.data?.token)
            editor.apply()
            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show()
            true
        }
    }

    /**
     * 注册
     */
    suspend fun register(newUserTdo: NewUserTdo): Boolean {
        val res = apiCall { UserApi.get().register(newUserTdo) }
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

            // 保存token 到sharedPreferences
            val sharedPreferences: SharedPreferences =
                getApplication<Application>()
                    .applicationContext
                    .getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("token", res?.data?.token)
            editor.apply()

            // 插入这个user
            insertUser(user)
            true;
        } else {
            Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            false
        }
    }



    /**
     * 判断用户名是否已存在
     */
    suspend fun isUsernameExist(userName: String) {
        val res = apiCall { UserApi.get().isUsernameExist(userName) }
        println(res)
    }

    /**
     * 加入家庭
     * @param inviterToken 邀请者token
     * @param familyCode 家庭id
     */
    suspend fun joinFamily(inviterToken: String, familyCode: Long) {
        val res = apiCall { UserApi.get().joinFamily(inviterToken, familyCode) }
        if (res.code == 200 && res.data != null) {
            var userList: List<NewUserTdo>? = res.data
            /**
             * 批量添加用户
             */
            for (user in userList!!) {
                var tempUser: User = User()
                tempUser.userId = user.userId
                tempUser.username = user.username
                tempUser.nickname = user.nickname
                tempUser.primaryUser = user.primaryUser
                tempUser.avatarUrl = user.avatarUrl
                tempUser.phone = user.phone
                tempUser.updateTime = user.updateTime
                tempUser.createTime = user.createTime
                tempUser.familyCode = user.familyCode

                tempUser.version = user.version

                insertUser(tempUser)
            }
        } else {
            Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 同步家庭
     */
    suspend fun synFamily() {
        var allUsers: List<User>? = userDao?.getAllNotLiveData()
        if (allUsers != null) {
            var res = apiCall { UserApi.get().synFamily(allUsers) }
            if (res.code == 200 && res.data != null) {
                for (user in res.data!!) {
                    var tempUser = userDao?.getUserById(user.userId!!)
                    if (tempUser != null) {
                        tempUser.username = user.username
                        tempUser.nickname = user.nickname
                        tempUser.familyCode = user.familyCode
                        tempUser.avatarUrl = user.avatarUrl
                        tempUser.updateTime = user.updateTime
                        tempUser.primaryUser = user.primaryUser
                        tempUser.phone = user.phone
                        tempUser.version = user.version
                        userDao?.update(tempUser)
                    } else {
                        var tempUser: User = User()
                        tempUser.userId = user.userId
                        tempUser.username = user.username
                        tempUser.nickname = user.nickname
                        tempUser.phone = user.phone
                        tempUser.primaryUser = user.primaryUser
                        tempUser.createTime = user.createTime
                        tempUser.updateTime = user.updateTime
                        tempUser.avatarUrl = user.avatarUrl
                        tempUser.familyCode = user.familyCode
                        tempUser.version = user.version
                        userDao?.insert(tempUser)
                    }
                }
            }
        }
    }

}

