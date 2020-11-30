package com.example.finalproject.database

class DayRepository(private val dayDao: DayDatabaseDao) {

    suspend fun getDay(date: String) {
        dayDao.get(date)
    }

    suspend fun addDay(day: Day) {
        dayDao.insert(day)
    }

    suspend fun updateDay(day: Day) {
        dayDao.update(day)
    }

    suspend fun deleteDay(date: String) {
        dayDao.delete(date)
    }
}