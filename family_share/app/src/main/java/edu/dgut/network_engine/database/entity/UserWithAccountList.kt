package edu.dgut.network_engine.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Edgar Liu
 *
 * 中间表 (一个用户有多个account)
 */
data class UserWithAccountList(
    @Embedded var user: User?,
    @Relation(
        parentColumn = "userId",
        entityColumn = "user"
    )
    var accountList: List<Account>?
)