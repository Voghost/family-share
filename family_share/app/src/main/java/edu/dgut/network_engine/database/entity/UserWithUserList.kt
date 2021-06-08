package edu.dgut.network_engine.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author edgar Liu
 * user 关联表  一个主用户有多个子用户
 */
data class UserWithUserList(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "primaryId"
    )
    val userList: List<User>
)
