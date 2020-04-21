package com.example.halp

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class BookingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_booking, container, false)
        val act = activity as MainActivity

        view.findViewById<DatePicker>(R.id.booking_date_picker).minDate = System.currentTimeMillis() - 1000

        view.findViewById<Button>(R.id.booking_reserve_button).setOnClickListener { v ->

            harverst(act);

            v.findNavController().navigate(R.id.action_bookingFragment_to_successFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as MainActivity

        fillFields(act);
    }

    fun fillFields(act: MainActivity)
    {
        view?.findViewById<TextView>(R.id.booking_price)?.text = act.quest?.cost.toString()
    }

    fun harverst(act: MainActivity) {
        val o: Order = Order()
        o.comment = view?.findViewById<TextInputEditText>(R.id.booking_comment)?.text.toString()
        o.people = view?.findViewById<TextInputEditText>(R.id.booking_people)?.text.toString().toInt()
        o.quest_id = act.quest?.id
        o.order_date = Date()
       // o.quest_date = view?.findViewById<DatePicker>(R.id.booking_date_picker)

    }
}
