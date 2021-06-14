package edu.dgut.network_engine.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.entity.User
import edu.dgut.network_engine.database.room_db.AccountDatabase
import kotlinx.coroutines.launch

class WalletViewModel : AndroidViewModel {
    private var accountDao: AccountDao? = null
    private var allAccountList: LiveData<List<Account>>

    constructor(application: Application) : super(application) {
        val accountDatabase: AccountDatabase = AccountDatabase.getInstance(application)
        accountDao = accountDatabase.getAccountDao()
        allAccountList = accountDao!!.getAll()
    }

    /**
     * 插入一条数据, 在新的线程中执行
     */
    fun insertAccount(account: Account) = viewModelScope.launch {
        accountDao?.insert(account)
    }

    /**
     * 删除一条数据, 在新的线程中执行
     */
    fun deleteUser(id: Long) = viewModelScope.launch {
        accountDao?.deleteById(id)
    }

    /**
     *更新一条数据，在新的线程中
     */
    fun updateUser(account: Account) = viewModelScope.launch {
        accountDao?.update(account)
    }
}