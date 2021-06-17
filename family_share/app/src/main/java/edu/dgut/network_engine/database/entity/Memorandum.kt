package edu.dgut.network_engine.database.entity

import com.google.gson.annotations.JsonAdapter
import java.util.*

data class Memorandum(
    var id: Long? = null,
    var content: String? = null,

    var createTime: Date? = null,
    var startTime: Date? = null,
    var endTime: Date? = null,
    var isFinish: Boolean? = null,
    var userId: Long? = null


) {
    override fun toString(): String {
        return "Memorandum(id=$id, content=$content, createTime=$createTime, startTime=$startTime, endTime=$endTime, isFinish=$isFinish, userId=$userId)"
    }
}
