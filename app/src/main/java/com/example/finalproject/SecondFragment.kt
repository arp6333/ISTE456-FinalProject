package com.example.finalproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.database.Day
import com.example.finalproject.database.DayViewModel
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    // Use this view model to populate
    //private lateinit var dayViewModel: DayViewModel
    private val dayViewModel: DayViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second, container, false)
//        val cl = view.findViewById<ConstraintLayout>(R.id.constraintLayout)
//        val spinner = cl.findViewById<Spinner>(R.id.loadingSpinner)

//        dayViewModel.isUpdating.observe(viewLifecycleOwner, {
//            if (it) spinner.visibility = View.VISIBLE else spinner.visibility = View.INVISIBLE
//        })

        // This code only runs when the viewmodel dayRecord value is REPLACED
        dayViewModel.dayRecord.observe(viewLifecycleOwner, {
            Toast.makeText(activity, "Observing", Toast.LENGTH_SHORT).show()
            if (it == null) {
                dayEntries.setText("No entries for this day.")
            }
            else {
                populateUIFromViewModel(it)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(activity, "onViewCreated", Toast.LENGTH_SHORT).show()
        // Populate the UI with the selected record
        populateUIFromViewModel(dayViewModel.dayRecord.value)

        button_cancel.setOnClickListener {
            // Go back to first fragment without changing anything
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        button_delete.setOnClickListener {
            // Delete, then go back to the first fragment
            if (dayViewModel.dayRecord.value == null) {
                // no record saved yet
                // TODO add toast message saying there is no record to delete yet
            }
            else {
                // TODO figure out the right way to delete record
            }
            Toast.makeText(activity, "Record Deleted.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        button_save.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)

            if (dayViewModel.dayRecord.value == null) {
                // new record
                dayViewModel.addDayRecord(editTextWakeUp.text.toString(),
                                          editTextInBed.text.toString(),
                                          ratingBar.rating,
                                          dayEntries.text.toString())
            }
            else {
                // updating record
                // TODO - finish statement: dayViewModel.updateDayRecord()
                //Toast.makeText(activity, "Entry Updated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateUIFromViewModel(day: Day?) {
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