package edu.dgut.network_engine.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.room_db.FamilyShareDatabase
import edu.dgut.network_engine.web_request.api.AccountApi
import edu.dgut.network_engine.web_request.apiCall
import edu.dgut.network_engine.web_request.tdo.AccountTdo
import kotlinx.coroutines.launch

class WalletViewModel : AndroidViewModel {
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
        var res = apiCall { AccountApi.get().delete(id) }
        if (res.code == 200) {
            accountDao?.deleteById(id)
            Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 更新帐目
     */
    fun update(account: Account) = viewModelScope.launch {
        account.version = account.version + 1
        accountDao?.update(account)
    }

    /**
     * 和云端同步
     */
    fun syn() = viewModelScope.launch {

        var accountList: List<Account>? = accountDao?.getAllNoLiveData()
        if (accountList != null) {
            var tdoList: ArrayList<AccountTdo> = ArrayList<AccountTdo>()
            for (account in accountList) {
                var temp = AccountTdo()
                temp.accountId = account.synId
                temp.createTime = account.createTime
                temp.updateTime = account.updateTime
                temp.hidden = account.hidden
                temp.isDeleted = account.isDeleted
                temp.price = account.price
                temp.reason = account.reason
                temp.userId = account.user
                temp.typeId = account.typeId
                tdoList.add(temp)
            }
            var res = apiCall { AccountApi.get().syn(tdoList) }
            if (res.code == 200 && res.data != null) {
                for (accountTdo: AccountTdo in res.data!!) {
                    var tempAccount = accountDao?.getBySynId(accountTdo.accountId!!)
                    if (tempAccount != null) {
                        if (accountTdo.isDeleted) {
                            accountDao?.deleteBySynId(accountTdo.accountId!!)
                        } else {
                            tempAccount.createTime = accountTdo.createTime
                            tempAccount.hidden = accountTdo.hidden
                            tempAccount.price = accountTdo.price
                            tempAccount.reason = accountTdo.reason
                            tempAccount.typeId = accountTdo.typeId
                            tempAccount.updateTime = accountTdo.updateTime
                            tempAccount.user = accountTdo.userId
                            accountDao?.update(tempAccount)
                        }
                    } else {
                        var tempAccount: Account = Account()
                        tempAccount.createTime = accountTdo.createTime
                        tempAccount.hidden = accountTdo.hidden
                        tempAccount.price = accountTdo.price
                        tempAccount.reason = accountTdo.reason
                        tempAccount.typeId = accountTdo.typeId
                        tempAccount.updateTime = accountTdo.updateTime
                        tempAccount.user = accountTdo.userId
                        tempAccount.synId = accountTdo.accountId
                        accountDao?.insert(tempAccount)
                    }
                }
            }

        }
    }
}