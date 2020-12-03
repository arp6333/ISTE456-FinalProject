package com.example.finalproject

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finalproject.database.Day
import com.example.finalproject.database.DayViewModel
import kotlinx.android.synthetic.main.fragment_second.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private val dayViewModel: DayViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dayViewModel.dayRecord.observe(viewLifecycleOwner, {
            if (it == null) dayEntries.setText("No entries for this day.") else updateUIFromViewModel(it)
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Populate the UI with the selected record
        updateUIFromViewModel(dayViewModel.dayRecord.value)

        button_cancel.setOnClickListener {
            // Go back to first fragment without changing anything
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        button_delete.setOnClickListener {
            // Delete, then go back to the first fragment
            if (dayViewModel.dayRecord.value == null) {
                // no record saved yet
                Toast.makeText(activity, "Entry has not been saved yet", Toast.LENGTH_SHORT).show()
            }
            else {
                dayViewModel.deleteDayRecord()
                Toast.makeText(activity, "Record Deleted.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }

        button_save.setOnClickListener {
            if (dayViewModel.dayRecord.value == null) {
                // new record
                saveEntries()
                Toast.makeText(activity, "Entry Saved", Toast.LENGTH_SHORT).show()
            }
            else {
                // updating record
                saveEntries()
                Toast.makeText(activity, "Entry Updated", Toast.LENGTH_SHORT).show()
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        initTimePicker(editTextWakeUp)
        initTimePicker(editTextInBed)
    }

    private fun initTimePicker(textView: TextView) {

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        textView.setOnClickListener {
            TimePickerDialog(activity, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }
    }

    // Calls the saveRecord method on the view model from the UI data
    private fun saveEntries() {
        dayViewModel.saveRecord(editTextWakeUp.text.toString(),
            editTextInBed.text.toString(),
            ratingBar.rating,
            dayEntries.text.toString())
    }

    // Updates the UI components with the values from the model
    private fun updateUIFromViewModel(day: Day?) {
        dayEntries.setText(day?.journal)
        editTextWakeUp.setText(day?.wakeTime)
        editTextInBed.setText(day?.bedTime)
        if (day != null) {
            if (day.rating > 5 || day.rating < 0) {
                ratingBar.rating = 0.toFloat()
            }
            else {
                ratingBar.rating = day.rating.toFloat()
            }
        }
    }
}