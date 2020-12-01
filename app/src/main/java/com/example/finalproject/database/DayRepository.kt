package com.example.finalproject.database

import androidx.lifecycle.MutableLiveData

class DayRepository(private val dayDao: DayDatabaseDao) {

    suspend fun getDay(date: String): Day? {
        return dayDao.get(date)
    }

    suspend fun addDay(day: Day): Boolean {
        return dayDao.insert(day) != null
    }

    suspend fun updateDay(day: Day): Boolean {
        return dayDao.insert(day) != null
    }

    suspend fun deleteDay(day: Day): Boolean {
        return dayDao.delete(day) != null
    }
}