package edu.dgut.network_engine.view_model

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.database.room_db.UserDatabase
import edu.dgut.network_engine.web_request.BaseResponse
import edu.dgut.network_engine.web_request.api.MyApi
import edu.dgut.network_engine.web_request.service.UserService
import edu.dgut.network_engine.web_request.tdo.Token
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import kotlin.math.log

class UserViewModel : AndroidViewModel {
    private var userDao: UserDao? = null
    private lateinit var allUserList: LiveData<List<User>>
    var okHttpClient = OkHttpClient()

    var retrofit: Retrofit = Retrofit.Builder().baseUrl("http://192.168.123.45:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var userService = retrofit.create(UserService::class.java)

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

/*
    */
    /**
     * 登陆
     *//*

    suspend fun login(userName: String, password: String) =
        withContext(viewModelScope.coroutineContext) {
            var loginMap = mapOf<String, String>("username" to userName, "password" to password)
            val res = MyApi.get().login(loginMap)
            if (res.code != 200 || res.data == null) {
                return@withContext null
            } else {
                return@withContext res
            }
        }
*/
    /**
     * 登陆
     */
    suspend fun login(userName: String, password: String): BaseResponse<Token>? {
        var loginMap = mapOf<String, String>("username" to userName, "password" to password)
        val res = MyApi.get().login(loginMap)
        return if (res.code != 200 || res.data == null) {
            null
        } else {
            res
        }
    }
}

