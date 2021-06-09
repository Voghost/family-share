package edu.dgut.network_engine.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.room_db.AccountDatabase
import kotlinx.coroutines.launch

class AccountViewModel : AndroidViewModel {
    private var accountDao: AccountDao? = null
    private var allAccountList: LiveData<List<Account>>

    constructor(application: Application) : super(application) {
        val accountDatabase: AccountDatabase = AccountDatabase.getInstance(application)
        accountDao = accountDatabase.getAccountDao()
        allAccountList = accountDao!!.getAll()
    }

    /**
     * 获取所有用户
     */
    fun getAll(): LiveData<List<Account>> {
        return this.allAccountList;
    }

    /**
     * 插入用户
     */
    fun insert(account: Account) = viewModelScope.launch {
        accountDao?.insert(account)
    }

    /**
     * 通过id删除用户u
     */
    fun delete(id: Long) = viewModelScope.launch {
        accountDao?.deleteById(id)
    }

    /**
     * 更新用户
     */
    fun update(account: Account) = viewModelScope.launch {
        accountDao?.update(account)
    }


}