package edu.dgut.network_engine.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.dgut.network_engine.database.converters.BigDecimalConverters
import java.math.BigDecimal

/**
 * @author Edgar Liu
 *
 * 帐单明细
 */
@Entity(tableName = "account_table")
@TypeConverters(value = [BigDecimalConverters::class])  //类型转化
data class Account(
    @PrimaryKey(autoGenerate = true)
    var accountId: Long? = null,        // 账户id
    var typeId: Long? = null,           // 帐目类型 外键->商品类型
    var price: BigDecimal? = null,      // 帐目价格
    var createTime: Long? = null,       // 创建时间
    var updateTime: Long? = null,       // 更新时间
    var user: Long? = null,             // 用户id
    var reason: String? = null,         // 帐目原因
    var hidden: Boolean = false,        // 是否对他人隐藏

    var isDeleted: Boolean = false,     // 是否删除(逻辑删除)
    var version: Long = 0               // 数据库版本号(后端同步使用)
)