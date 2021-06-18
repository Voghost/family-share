package edu.dgut.network_engine.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.MemorandumDao
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.room_db.FamilyShareDatabase
import edu.dgut.network_engine.web_request.api.MemorandumApi
import edu.dgut.network_engine.web_request.apiCall
import kotlinx.coroutines.launch

class MemorandumViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var userDao: UserDao
    private lateinit var memorandumDao: MemorandumDao
    private lateinit var allMemorandums: LiveData<List<Memorandum>>

    init {
        val familyShareDatabase: FamilyShareDatabase = FamilyShareDatabase.getInstance(application)
        userDao = familyShareDatabase.getUserDao()
        memorandumDao = familyShareDatabase.getMemorandumDao()
        allMemorandums = memorandumDao.getAll()
    }


    /**
     * 获取livedata
     */
    fun getLiveData(): LiveData<List<Memorandum>> {
        getAllMemorandum()
        return allMemorandums
    }

    /**
     * 获取所有数据
     */
    private fun getAllMemorandum() {
        viewModelScope.launch {
            var res = apiCall { MemorandumApi.get().all() }
            if (res.code == 200 && res.data != null) {

                for (memorandum in res.data!!) {
                    memorandum.username = userDao?.getUserById(memorandum.id!!)?.username
                }
                memorandumDao.deleteAll() // 先删除全部
                memorandumDao.insertAll(res.data!!) //保存到数据库
            } else {
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    /**
     * 新增一条备忘录
     */
    fun insertMemorandum(memorandum: Memorandum) {
        viewModelScope.launch {
            var res = apiCall { MemorandumApi.get().insert(memorandum) }
            if (res.code == 200 && res.data != null) {
                memorandumDao.insert(res.data!!) // 保存到数据库
                Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 更新一条备忘录
     */
    fun updateMemorandum(memorandum: Memorandum) {
        viewModelScope.launch {
            var res = apiCall { MemorandumApi.get().update(memorandum) }
            if (res.code == 200 && res.data != null) {
                memorandumDao.update(res.data!!) //保存到数据库
                Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 删除一条备忘录
     */
    fun deleteMemorandum(id: Long) {
        viewModelScope.launch {
            var res = apiCall { MemorandumApi.get().delete(id) }
            if (res.code == 200 && res.data != null) {
                memorandumDao.deleteById(id)
                Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}