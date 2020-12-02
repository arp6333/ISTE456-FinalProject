package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.database.DayViewModel
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    // Use this view model to populate
    private lateinit var dayViewModel: DayViewModel

    // Perform non-graphical initializations here
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
            if (it == null) {
                dayEntries.text = "No entries for this day."
            }
            else {
                dayEntries.text = it?.journal
                editTextWakeUp.setText(it?.wakeTime)
                editTextInBed.setText(it?.bedTime)
                if (it?.rating > 5 || it?.rating < 0) {
                    ratingBar.rating = 0.toFloat()
                }
                else {
                    ratingBar.rating = it?.rating.toFloat()
                }
            }
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            // Save, then go back to the first fragment
            if (dayViewModel.dayRecord.value == null) {
                // new record
                dayViewModel.addDayRecord(editTextWakeUp.text.toString(),
                                          editTextInBed.text.toString(),
                                          ratingBar.rating,
                                          dayEntries.text.toString())
                Toast.makeText(activity, "Record Created.", Toast.LENGTH_SHORT).show()
            }
            else {
                // updating record
                // TODO - finish statement: dayViewModel.updateDayRecord()
                Toast.makeText(activity, "Record Updated.", Toast.LENGTH_SHORT).show()
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

}