package com.example.finalproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Saves the state of the application such as the selected date, and the model that is being
 * used.
 */
class DayViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DayRepository
    private var _dayRecord = MutableLiveData<Day>() // day.value is null if no record
    val dayRecord : LiveData<Day>
        get() = _dayRecord
    private var selectedDate: String = ""

    init {
        // Initialize the database and repository
        val dayDao = DayDatabase.getInstance(application).dayDatabaseDao()
        repository = DayRepository(dayDao)
    }

    // Called when a new date is selected, or when the calendar view is initialized
    fun updateSelectedDate(year: Int, month: Int, day: Int) {
        val date = LocalDate.of(year, month, day)
        val df = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = date.format(df)
        selectedDate = formattedDate

        // Get the record for the selected date if exists.
        viewModelScope.launch(Dispatchers.IO) {
            _dayRecord.postValue(repository.getDay(formattedDate))
        }
    }

    // Update or Add a new day record if selectedDate is has not been saved
    fun saveRecord(wakeTime: String, bedTime: String, rating: Float, entries: String) {
        val day = Day(selectedDate, rating.toDouble(), entries, wakeTime, bedTime)
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.saveDay(day)) {
                // insert success
                _dayRecord.postValue(day)
            }
            else {
                // insert failed
                _dayRecord.postValue(null)
            }
        }
    }

    fun deleteDayRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.deleteDay(dayRecord.value!!)) {
                _dayRecord.postValue(null)
            }
        }
    }

    fun getSelectedDate(): String {
        return selectedDate
    }
}
