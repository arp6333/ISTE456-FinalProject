package com.example.finalproject.database

class DayRepository(private val dayDao: DayDatabaseDao) {

    // Get the day object from a given date string
    suspend fun getDay(date: String): Day? {
        return dayDao.get(date)
    }

    // Save the day object to the database
    suspend fun saveDay(day: Day): Boolean {
        return dayDao.insert(day) != null
    }

    // Delete the day object from the database
    suspend fun deleteDay(day: Day): Boolean {
        return dayDao.delete(day) != null
    }
}