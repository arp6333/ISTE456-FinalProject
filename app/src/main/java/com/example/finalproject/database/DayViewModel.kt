package com.example.finalproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DayViewModel(application: Application): AndroidViewModel(application) {

    // Holds the LiveData object

    private val repository: DayRepository
    private var isUpdating: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    private var _dayRecord = MutableLiveData<Day>() // day.value is null if no record
    val dayRecord : LiveData<Day>
        get() = _dayRecord
    private var selectedDate: String = ""


    init {
        val dayDao = DayDatabase.getInstance(application).dayDatabaseDao()
        repository = DayRepository(dayDao)
    }

    // Called when a new date is selected
    fun updateSelectedDate(date: Date) {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = df.format(date)
        selectedDate = formattedDate
        viewModelScope.launch(Dispatchers.IO) {
            _dayRecord.postValue(repository.getDay(formattedDate))
        }
    }

    // should update the live data object and also add the record to the database
    fun addDayRecord(wakeTime: String, bedTime: String, rating: Float, entries: String) {
        val day = Day(selectedDate, rating.toDouble(), entries, wakeTime, bedTime)
        isUpdating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.addDay(day)) {
                // insert success
                _dayRecord.postValue(day)
            }
            else {
                _dayRecord.postValue(null)
            }
            isUpdating.postValue(false)
        }
    }

    // should update the live data object and also update the record in the database
    fun updateDayRecord(day: Day) {
        isUpdating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.updateDay(day)) {
                // update success
                _dayRecord.postValue(day)
            }
            else {
                _dayRecord.postValue(null)
            }
            isUpdating.postValue(false)
        }
    }

    //
    fun deleteDayRecord(day: Day) {
        isUpdating.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.deleteDay(day)) {
                _dayRecord.postValue(null)
            }
            isUpdating.postValue(false)
        }
    }

    fun getIsUpdating(): Boolean {
        return isUpdating.value!!
    }

}
// setvalue for UI thread, postvalue for background thread