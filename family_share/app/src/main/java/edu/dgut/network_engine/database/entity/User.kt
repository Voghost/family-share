package edu.dgut.network_engine.database.entity

import androidx.room.*

/**
 * @author Edgar Liu
 *
 * 用户实体
 */
@Entity(
    tableName = "user_table",
    indices = [Index(value = ["username"], unique = true)],
)
data class User(
    @PrimaryKey(autoGenerate = true)
    var userId: Long? = null,        // 用户id
    var username: String? = null,    // 用户名称 唯一
    var nickname: String? = null,    // 用户昵称
    var phone: String? = null,       // 用户电话
    var primaryUser: Long? = null,   // 主用户 (外键- User->userId)
    var createTime: Long? = null,    // 创建时间
    var updateTime: Long? = null,    // 更新时间
    var avatarUrl: String? = null,   // 头像URL
    var isMe: Boolean? = null,       // 是否为当前用户
    var familyCode: Long? = null,    // 家庭code

    var version: Long = 0            // 当前数据版本号
)
