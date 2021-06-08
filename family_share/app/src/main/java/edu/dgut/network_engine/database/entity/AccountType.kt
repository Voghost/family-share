package edu.dgut.network_engine.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * @author Edgar Liu
 * 帐目类型 (如: 交通、伙食、教育等)
 */
@Entity(
    tableName = "account_type_table",
    indices = [Index(value = ["userName"], unique = true)]
)
data class AccountType(
    @PrimaryKey(autoGenerate = true)
    var accountTypeId: Long,
    var accountName: String
)
