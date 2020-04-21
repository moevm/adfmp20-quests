package com.example.halp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.navigation.findNavController

class BookingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)

        view.findViewById<DatePicker>(R.id.booking_date_picker).minDate = System.currentTimeMillis() - 1000

        view.findViewById<Button>(R.id.booking_reserve_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_bookingFragment_to_successFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
