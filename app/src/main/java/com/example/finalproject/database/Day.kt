package com.example.finalproject.database

import androidx.room.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "days")
data class Day(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "rating")
    val rating: Int = 0,

    @ColumnInfo(name = "journal")
    val journal: String = "",
)

class DateTypeConverter {
    @TypeConverter
    fun dateToString(date: LocalDate): String? {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

    @TypeConverter
    fun stringToDate(date: String): LocalDate {
        return LocalDate.parse(date)
    }
}
