package com.example.finalproject.database

import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface DayDatabaseDao {
    // Get an existing day record
    @Query("SELECT * FROM days WHERE date = :date")
    suspend fun get(date: String): Day?

    // Insert a new day record
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(day: Day): Long?

    // Delete an existing day record, will return an int
    @Delete
    suspend fun delete(day: Day): Int?
}