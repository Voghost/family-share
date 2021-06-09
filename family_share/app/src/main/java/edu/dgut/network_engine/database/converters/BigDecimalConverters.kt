package edu.dgut.network_engine.database.converters

import androidx.room.TypeConverter
import java.math.BigDecimal

/**
 * @author Edgar Liu
 * 类型转化
 */
object BigDecimalConverters {
    /*   @TypeConverter
       fun fromString(value: String?): BigDecimal? {
           return value?.let { BigDecimal(it) }
       }

       @TypeConverter
       fun amountToString(bigDecimal: BigDecimal?): Double? {
           return bigDecimal?.toDouble()
       }*/
    @TypeConverter
    fun fromLong(value: Long?): BigDecimal? {
        return if (value == null) null else BigDecimal(value).divide(BigDecimal(100))
    }

    @TypeConverter
    fun toLong(bigDecimal: BigDecimal?): Long? {
        return bigDecimal?.multiply(BigDecimal(100))?.toLong()
    }

}