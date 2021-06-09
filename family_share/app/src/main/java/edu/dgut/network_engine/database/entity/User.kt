package edu.dgut.network_engine.database.entity

import androidx.room.*

/**
 * @author edgar Liu
 * 用户实体
 */
@Entity(
    tableName = "user_table",
    indices = [Index(value = ["userName"], unique = true)],
)
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Long,       // 用户id
    var userName: String?,   // 用户名称 唯一
    var nickname: String?,   // 用户昵称
    var phone: String?,      // 用户电话
    var primaryUser: Long?,  // 主用户 (外键- User->userId)
    var createTime: Long,    // 创建时间
    var updateTime: Long,    // 更新时间
    var avatarUrl: String?,    // 头像URL
    var isMe: Boolean        // 是否为当前用户
)
