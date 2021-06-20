package edu.dgut.network_engine.view_model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.dao.MemorandumDao
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.entity.UserWithAccountList
import edu.dgut.network_engine.database.room_db.FamilyShareDatabase
import edu.dgut.network_engine.web_request.api.UserApi
import edu.dgut.network_engine.web_request.apiCall
import edu.dgut.network_engine.web_request.tdo.NewUserTdo
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.*


class UserViewModel(application: Application) : AndroidViewModel(application) {
    private var userDao: UserDao? = null
    private var accountDao: AccountDao? = null
    private var memorandumDao: MemorandumDao? = null
    private lateinit var allUserList: LiveData<List<User>>

    init {
        val familyShareDatabase: FamilyShareDatabase = FamilyShareDatabase.getInstance(application)
        userDao = familyShareDatabase.getUserDao()
        accountDao = familyShareDatabase.getAccountDao()
        memorandumDao = familyShareDatabase.getMemorandumDao()
        allUserList = userDao!!.getAll()
    }

    /**
     * 获取所有用户, LiveData 无需再开辟新线程
     */
    fun getAllUserList(): LiveData<List<User>> {
        return allUserList
    }

    suspend fun getUserById(id: Long) = withContext(viewModelScope.coroutineContext) {
        userDao?.getUserById(id)
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
     * 获取用户信息
     */
    suspend fun getUserByUserId(id: Long): User? {
        var user = userDao?.getUserById(id)
        return user
    }

    fun changePass(lastPass: String, newPass: String) {
        viewModelScope.launch {
            var user = userDao?.getMe()
            var res = apiCall { UserApi.get().changePassword(user?.userId!!, lastPass, newPass) }
            if (res.code == 200) {
                Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun changInfo(nickName: String, phoneNum: String) {
        viewModelScope.launch {
            var user = userDao?.getMe()
            var res = apiCall { UserApi.get().updateInfo(user?.userId!!, nickName, phoneNum) }
            if (res.code == 200) {
                Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show()
            }

        }
    }


    /**
     * 退出登陆
     */
    fun logout() {
        viewModelScope.launch {
            apiCall { UserApi.get().logout() }
            accountDao?.deleteAll()
            userDao?.deleteAll()
            memorandumDao?.deleteAll()
        }
    }

    /**
     * 退出家庭
     */
    fun quitFamily() {
        viewModelScope.launch {
            var me = userDao?.getMe()
            var res = apiCall { UserApi.get().quitFamily() }
            if (res.code == 200) {
                Toast.makeText(getApplication(), "退出成功", Toast.LENGTH_SHORT).show()
                if (me != null) {
                    accountDao?.deleteAll()
                    memorandumDao?.deleteAll()
                    userDao?.deleteNotEqual(me.userId!!)
                }

            } else {
                Toast.makeText(getApplication(), "退出失败", Toast.LENGTH_SHORT).show()
            }
        }
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
            user.isMe = true
            userDao?.insert(user)
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
            user.isMe = true // 注册的是当前用户
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
            userDao?.insert(user)
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
    suspend fun joinFamily(inviterToken: String) {
        val res = apiCall { UserApi.get().joinFamily(inviterToken) }
        if (res.code == 200 && res.data != null) {
            var userList: List<NewUserTdo>? = res.data
            var me = userDao?.getMe()
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
                if (user.userId == me?.userId) {
                    tempUser.isMe = true
                }
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
                        if (tempUser.familyCode == tempUser.userId && tempUser.isMe == false) {
                            // 如果family code 指向自己 且 不为当前用户, 则删除
                            userDao?.delete(tempUser.userId!!)
                        } else {
                            tempUser.username = user.username
                            tempUser.nickname = user.nickname
                            tempUser.familyCode = user.familyCode
                            tempUser.avatarUrl = user.avatarUrl
                            tempUser.updateTime = user.updateTime
                            tempUser.primaryUser = user.primaryUser
                            tempUser.phone = user.phone
                            tempUser.version = user.version
                            userDao?.update(tempUser)
                        }
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

    // 文件上传
    suspend fun upload(bitmap: Bitmap) {

        var bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        var bitmapData: ByteArray = bos.toByteArray()

        val fileRQ: RequestBody = RequestBody.create("image/JPEG".toMediaTypeOrNull(), bitmapData)
        var part: MultipartBody.Part =
            MultipartBody.Part.Companion.createFormData("file", "file.jpg", fileRQ)
        viewModelScope.launch {
            var res = apiCall { UserApi.get().upload(part) }
            if (res.code == 200 && res.data != null) {
                var me = userDao?.getMe()
                me?.avatarUrl = res.data!!.url
                me?.version = me?.version?.plus(1)
                userDao?.update(me!!)

            } else {
                println(res)
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    //删除全部
    suspend fun deleteAll() {
        userDao?.deleteAll()
    }


}

