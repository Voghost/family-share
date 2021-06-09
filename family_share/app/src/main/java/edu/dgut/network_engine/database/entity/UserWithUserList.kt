package edu.dgut.network_engine.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import java.lang.reflect.Constructor

/**
 * @author Edgar Liu
 * user 关联表  一个主用户有多个子用户
 */
data class UserWithUserList(
    @Embedded var user: User?,
    @Relation(
        parentColumn = "userId",
        entityColumn = "primaryUser"
    )
    var userList: List<User>?
)

