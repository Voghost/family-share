package edu.dgut.network_engine.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.entity.Memorandum
import edu.dgut.network_engine.web_request.api.MemorandumApi
import edu.dgut.network_engine.web_request.apiCall
import kotlinx.coroutines.launch

class MemorandumViewModel(application: Application) : AndroidViewModel(application) {
    private val allMemorandums = MutableLiveData<List<Memorandum>>()

    /**
     * 获取livedata
     */
    fun getLiveData(): MutableLiveData<List<Memorandum>> {
        getAllMemorandum()
        return allMemorandums
    }

    /**
     * 获取所有数据
     */
    fun getAllMemorandum(){
        viewModelScope.launch {
            var res = apiCall { MemorandumApi.get().all() }
            if (res.code == 200 && res.data != null) {
                allMemorandums.postValue(res.data)
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
                Toast.makeText(getApplication(), "添加成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }
        }
        getAllMemorandum()
    }

    /**
     * 更新一条备忘录
     */
    fun updateMemorandum(memorandum: Memorandum) {
        viewModelScope.launch {
            var res = apiCall { MemorandumApi.get().update(memorandum) }
            if (res.code == 200 && res.data != null) {
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
                Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
            }

        }
    }


}