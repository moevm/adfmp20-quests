package com.example.halp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class FavouritesListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_favourites_list, container, false)

        view.findViewById<Button>(R.id.quest_list_filter_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.filterFragment)
        }
        view.findViewById<Button>(R.id.quest_item_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_navigation_favourites_to_questCardFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val act = activity as MainActivity
        if( act.user == null )
            view.findNavController().navigate(R.id.loadingFragment)
    }
}
