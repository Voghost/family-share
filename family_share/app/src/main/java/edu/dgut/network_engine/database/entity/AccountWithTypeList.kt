package edu.dgut.network_engine.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Edgar Liu
 *
 * 中间表 (一个account 包含多个 type)
 */
data class AccountWithTypeList(
    @Embedded val account: Account?,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "accountTypeId"
    )
    val accountList: List<Account>?
)