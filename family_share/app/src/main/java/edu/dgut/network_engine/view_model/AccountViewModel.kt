package edu.dgut.network_engine.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.room_db.FamilyShareDatabase
import kotlinx.coroutines.launch

class AccountViewModel : AndroidViewModel {
    private var accountDao: AccountDao? = null
    private var allAccountList: LiveData<List<Account>>

    constructor(application: Application) : super(application) {
        val familyShareDatabase: FamilyShareDatabase = FamilyShareDatabase.getInstance(application)
        accountDao = familyShareDatabase.getAccountDao()
        allAccountList = accountDao!!.getAll()
    }

    /**
     * 获取所有帐目
     */
    fun getAll(): LiveData<List<Account>> {
        return this.allAccountList;
    }

    /**
     * 插入帐目
     */
    fun insert(account: Account) = viewModelScope.launch {
        accountDao?.insert(account)
    }

    /**
     * 通过id删除帐目
     */
    fun delete(id: Long) = viewModelScope.launch {
        accountDao?.deleteById(id)
    }

    /**
     * 更新帐目
     */
    fun update(account: Account) = viewModelScope.launch {
        accountDao?.update(account)
    }


}