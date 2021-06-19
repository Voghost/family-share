package edu.dgut.network_engine.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import edu.dgut.network_engine.database.dao.AccountDao
import edu.dgut.network_engine.database.dao.UserDao
import edu.dgut.network_engine.database.entity.Account
import edu.dgut.network_engine.database.room_db.FamilyShareDatabase
import edu.dgut.network_engine.web_request.api.AccountApi
import edu.dgut.network_engine.web_request.apiCall
import edu.dgut.network_engine.web_request.tdo.AccountTdo
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class AccountViewModel : AndroidViewModel {
    private var accountDao: AccountDao? = null
    private var userDao: UserDao? = null
    private var allAccountList: LiveData<List<Account>>

    constructor(application: Application) : super(application) {
        val familyShareDatabase: FamilyShareDatabase = FamilyShareDatabase.getInstance(application)
        accountDao = familyShareDatabase.getAccountDao()
        userDao = familyShareDatabase.getUserDao()
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
        var user = userDao?.getMe()
        if (account!!.user != user!!.userId) {
            Toast.makeText(getApplication(), "只能新增当前用户的信息", Toast.LENGTH_SHORT).show()
        } else {
            account.version = 0
            accountDao?.insert(account)
        }
    }

    /**
     * 通过id删除帐目
     */
    fun delete(id: Long) = viewModelScope.launch {
        var user = userDao?.getMe()
        var acc = accountDao?.getById(id)
        if (acc!!.user != user!!.userId) {
            Toast.makeText(getApplication(), "只能删除当前用户的信息", Toast.LENGTH_SHORT).show()
        } else {
            if (acc?.synId != null) {
                var res = apiCall { AccountApi.get().delete(acc?.synId!!) }
                if (res.code == 200) {
                    accountDao?.deleteById(id)
                    Toast.makeText(getApplication(), "删除成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(getApplication(), res.message, Toast.LENGTH_SHORT).show()
                }
            } else {
                accountDao?.deleteById(id)
            }
        }
    }

    /**
     * 更新帐目
     */
    fun update(account: Account) = viewModelScope.launch {
        var updateAccount = accountDao?.getById(account.accountId!!)

        var user = userDao?.getMe()
        if (updateAccount!!.user != user!!.userId) {
            Toast.makeText(getApplication(), "只能修改当前用户的信息", Toast.LENGTH_SHORT).show()
        }else{
            updateAccount!!.version = updateAccount.version + 1
            updateAccount.price = account.price
            updateAccount.reason = account.reason
            updateAccount.updateTime = Date().time
            accountDao?.update(updateAccount)
        }
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
                temp.androidId = account.accountId
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
                        var localAccount = accountDao?.getById(accountTdo.androidId!!)
                        if (localAccount != null && localAccount.synId == null) {
                            localAccount.synId = accountTdo.accountId
                            accountDao?.update(localAccount)
                        } else {
                            if (!accountTdo.isDeleted) {
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
    }

}