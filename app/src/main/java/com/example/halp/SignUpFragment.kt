package com.example.halp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController

class SignUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_signup, container, false)

        view.findViewById<Button>(R.id.signup_signup_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        view.findViewById<TextView>(R.id.signup_signin_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
