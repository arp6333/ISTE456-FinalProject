package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.finalproject.database.DayViewModel
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var dayViewModel: DayViewModel // use this view model to populate

    // perform non-graphical initializations here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dayViewModel = ViewModelProvider(this).get(DayViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        dayViewModel.dayRecord.observe(viewLifecycleOwner, {
            dayEntries.text = it.journal
            // TODO - Ellie - figure out how to set the rest of the fields
            // 'it' could be null if there is no record
            // 'it' is the Day object - it has the fields you need
            // When the Day object is updated the UI will automatically update
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewSelectedDate.text = arguments?.getString("dayRecord")

        button_cancel.setOnClickListener {
            // go back to first fragment without changing anything
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        button_delete.setOnClickListener {
            // delete, then go back to the first fragment
            //delete
            if (dayViewModel.dayRecord.value == null) {
                // no record saved yet
                // TODO add toast message saying there is no record to delete yet
            }
            else {
                // TODO figure out the right way to delete record
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        button_save.setOnClickListener {
            // save, then go back to the first fragment
            if (dayViewModel.dayRecord.value == null) {
                // new record
                // TODO - finish statement: dayViewModel.addDayRecord()
            }
            else {
                // updating record
                // TODO - finish statement: dayViewModel.updateDayRecord()
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}