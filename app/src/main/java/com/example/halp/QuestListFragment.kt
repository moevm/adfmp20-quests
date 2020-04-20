package com.example.halp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.GeoPoint

class QuestListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_quest_list, container, false)

        view.findViewById<Button>(R.id.quest_list_filter_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.filterFragment)
        }

        updateList(view);
        /*view.findViewById<Button>(R.id.quest_item_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.action_navigation_search_to_questCardFragment)
        }*/

        return view
    }

    fun updateList(v: View) {
        val quests: ArrayList<Quest> = ArrayList()
        val act = activity as MainActivity
        act.db.collection("quests").get().addOnSuccessListener { snapshot ->
            for( doc in snapshot )
            {
                val q = Quest()
                    q.id = doc.id
                    q.name = doc["name"] as String
                    q.description =  doc["description"] as String
                    q.duration = doc["duration"] as Number
                    q.min_people = doc["min_people"] as Number
                    q.max_people = doc["max_people"] as Number
                    q.company =  doc["company"] as String
                    q.complexity =  doc["complexity"] as String
                    q.genre =  doc["genre"] as String
                    q.address = doc["address"] as GeoPoint
                    q.cost = doc["cost"] as Number
                    q.img_url =  doc["img_url"] as String
                quests.add(q)
            }
        }

        val rv: RecyclerView = v.findViewById(R.id.quest_list_recycle)
        val rva = QuestListAdapter(quests)
        rv.adapter = rva
        rv.layoutManager = LinearLayoutManager(context)
    }

/*    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.let { updateList(it) }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
