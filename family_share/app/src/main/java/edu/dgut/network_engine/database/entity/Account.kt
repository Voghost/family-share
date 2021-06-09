package edu.dgut.network_engine.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import edu.dgut.network_engine.database.converters.Converters
import java.math.BigDecimal
import java.util.*

/**
 * @author Edgar Liu
 *
 * 帐单明细
 */
@Entity(tableName = "account_table")
@TypeConverters(Converters::class)  //类型转化
data class Account(
    @PrimaryKey(autoGenerate = true)
    var accountId: Long,        // 用户id
    var typeId: Long,           // 帐目类型 外键->商品类型
    var price: BigDecimal,      // 帐目价格
    var createTime: Long,       // 创建时间
    var updateTime: Long,       // 更新时间
    var userId: Long,           // 用户id
    var case: String,           // 帐目原因
    var hidden: Boolean         // 是否对他人隐藏
)