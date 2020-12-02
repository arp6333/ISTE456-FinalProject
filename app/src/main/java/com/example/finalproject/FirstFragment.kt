package com.example.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.database.DayViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 * UI For the calendar view
 */
class FirstFragment : Fragment() {

    // Date format is yyyy-MM-dd
    private lateinit var dayViewModel: DayViewModel // use this view model to populate

    // perform non-graphical initializations here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dayViewModel.dayRecord.observe(viewLifecycleOwner, {
            dayEntries.text = it?.journal
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val today = Date()
        calendarView.date = today.time // set the calendar to have today selected
        dayViewModel.updateSelectedDate(today) // fetches record for the selected date
        initListeners()
    }

    private fun initListeners() {
        button_first.setOnClickListener {
            // Navigate to edit page
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        calendarView.setOnDateChangeListener { _, _, _, _ ->
            dayViewModel.updateSelectedDate(Date(calendarView.date))
        }
    }
}

// Setting the day (and oncreate) should tell the ViewModel what the new selected date is