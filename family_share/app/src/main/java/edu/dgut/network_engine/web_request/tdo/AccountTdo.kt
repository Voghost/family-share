package edu.dgut.network_engine.web_request.tdo

import java.math.BigDecimal

class AccountTdo {
    var accountId: Long? = null        // 账户id 对于account的线上同步id
    var typeId: Long? = null           // 帐目类型 外键->商品类型
    var price: BigDecimal? = null      // 帐目价格
    var createTime: Long? = null       // 创建时间
    var updateTime: Long? = null       // 更新时间
    var userId: Long? = null           // 用户id
    var reason: String? = null         // 帐目原因
    var hidden: Boolean = false        // 是否对他人隐藏
    var isDeleted: Boolean = false     // 是否删除(逻辑删除)
    var androidId: Long? = null
    var version: Long = 0              // 当前数据版本号(后端同步使用)
}
