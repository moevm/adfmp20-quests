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
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.GeoPoint
import com.yandex.mapkit.geometry.Point

class FavouritesListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_favourites_list, container, false)

        /*view.findViewById<Button>(R.id.quest_list_filter_button).setOnClickListener { v ->
            v.findNavController().navigate(R.id.filterFragment)
        }*/

        return view
    }

    fun updateList(v: View) {
        val quests: ArrayList<Quest> = ArrayList()
        val act = activity as MainActivity
        if( act.user?.favourites?.isNotEmpty()!! )
            act.user?.favourites?.let {
                act.db.collection("quests").whereIn(FieldPath.documentId(), it)
                    .get().addOnSuccessListener { snapshot ->
                    for( doc in snapshot ) {
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
                        q.address = doc["address"] as String
                        q.cost = doc["cost"] as Number
                        q.phone = doc["phone"] as String
                        q.coords = Point((doc["coords"] as GeoPoint).latitude, (doc["coords"] as GeoPoint).longitude)
                        q.img_url =  doc["img_url"] as String
                        quests.add(q)
                    }
                }.addOnCompleteListener {
                    val rv: RecyclerView = v.findViewById(R.id.quest_list_recycle)
                    val rva = QuestListAdapter(quests, act)
                    rv.adapter = rva
                    rv.layoutManager = LinearLayoutManager(context)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val act = activity as MainActivity
        if( act.user == null )
            view.findNavController().navigate(R.id.loadingFragment)
        else
            updateList(view)
    }
}
