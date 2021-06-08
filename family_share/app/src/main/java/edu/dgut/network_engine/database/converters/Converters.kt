package edu.dgut.network_engine.database.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * @author Edgar Liu
 * 类型转化
 */
object Converters {
    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun amountToString(bigDecimal: BigDecimal?): Double? {
        return bigDecimal?.toDouble()
    }
}