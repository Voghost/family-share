package edu.dgut.network_engine.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.JsonAdapter
import java.util.*

@Entity(tableName = "memorandum_table")
data class Memorandum(
    @PrimaryKey
    var id: Long? = null,
    var content: String? = null,

    var createTime: Date? = null,
    var startTime: Date? = null,
    var endTime: Date? = null,
    var isFinish: Boolean? = null,
    var userId: Long? = null,
    var username: String? = null


) {
    override fun toString(): String {
        return "Memorandum(id=$id, content=$content, createTime=$createTime, startTime=$startTime, endTime=$endTime, isFinish=$isFinish, userId=$userId)"
    }
}
