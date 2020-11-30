package com.example.finalproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DayRepository

    init {
        val dayDao = DayDatabase.getInstance(application).dayDatabaseDao()
        repository = DayRepository(dayDao)
    }

    fun addDayRecord(day: Day) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addDay(day)
        }
    }

    fun updateDayRecord(day: Day) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDay(day)
        }
    }

    fun getDayRecord(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDay(date)
        }
    }

    fun deleteDayRecord(date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDay(date)
        }
    }

}