package com.example.finalproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_first.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var curDate: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("MM / dd / yyyy", Locale.getDefault())
        curDate = df.format(c)

        button_first.setOnClickListener {
            val bundle = bundleOf("date" to curDate)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // month starts at 0 so we add 1 to it
            var actualMonth = month + 1
            curDate = "$actualMonth / $dayOfMonth / $year"
        }
    }
}