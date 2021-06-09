package edu.dgut.network_engine.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Edgar Liu
 *
 * 中间表 (一个用户有多个account)
 */
data class UserWithAccountList(
    @Embedded val user: User?,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val accountList: List<Account>
)