package com.example.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.database.DayViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.text.DateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 * UI For the calendar view
 */
class FirstFragment : Fragment() {

    // Date format is yyyy-MM-dd
    private val dayViewModel: DayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dayViewModel.dayRecord.observe(viewLifecycleOwner, {
            dayEntries.text = it?.journal ?: "No entries for this day."
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateString = dayViewModel.getSelectedDate()
        if (dateString != "") {
            initCalendar(LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE))
        }
        else {
            initCalendar(LocalDate.now())
        }
        initListeners()
    }

    private fun initCalendar(date: LocalDate) {
        // set the calendar to have today selected
        calendarView.date = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        // update the view model with the selected date
        dayViewModel.updateSelectedDate(date.year, date.monthValue, date.dayOfMonth)
    }

    private fun initListeners() {
        button_first.setOnClickListener {
            // Navigate to edit page
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            dayViewModel.updateSelectedDate(year, month + 1, dayOfMonth)
        }
    }
}
