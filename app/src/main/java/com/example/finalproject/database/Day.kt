package com.example.finalproject.database

import androidx.room.*

@Entity(tableName = "days")
data class Day(
    @PrimaryKey
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "rating")
    val rating: Double = 0.0,

    @ColumnInfo(name = "journal")
    val journal: String = "",

    @ColumnInfo(name = "wake_time")
    val wakeTime: String = "",

    @ColumnInfo(name = "bed_time")
    val bedTime: String = ""
)
