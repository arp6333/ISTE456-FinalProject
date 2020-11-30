package com.example.finalproject.database

import androidx.room.*

@Dao
interface DayDatabaseDao {
    // Insert a new day record
    @Insert
    suspend fun insert(day: Day)

    // Update an existing day record
    @Update
    suspend fun update(day: Day)

    // Get an existing day record
    @Query("SELECT * FROM days WHERE date = :date")
    suspend fun get(date: String): Day?

    // Delete an existing day record
    @Delete
    suspend fun delete(key: String)
}