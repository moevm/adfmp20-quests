package com.example.halp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestListAdapter(val quests: ArrayList<Quest>) :
    RecyclerView.Adapter<QuestListAdapter.QuestItemHolder>()
{

    class QuestItemHolder(view: View) :
        RecyclerView.ViewHolder(view)
    {
        var company: TextView = view.findViewById(R.id.quest_item_company)
        var name: TextView = view.findViewById(R.id.quest_item_name)
        var people: TextView = view.findViewById(R.id.quest_item_people)
        var duration: TextView = view.findViewById(R.id.quest_item_duration)
        var complexity: TextView = view.findViewById(R.id.quest_item_complexity)
        var cost: TextView = view.findViewById(R.id.quest_item_cost)

        var informationButton: Button = view.findViewById(R.id.quest_item_button)
        var image: ImageView = view.findViewById(R.id.quest_item_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_quest, parent, false)
        return QuestItemHolder(view)
    }

    override fun getItemCount(): Int {
        return quests.size
    }

    override fun onBindViewHolder(holder: QuestItemHolder, position: Int) {
        holder.company.text = quests[position].company
        holder.name.text = quests[position].name
        holder.people.text = (quests[position].min_people.toString() + " - " + quests[position].max_people.toString())
        holder.complexity.text = quests[position].complexity
        holder.duration.text = (quests[position].duration.toString() + " min")
        holder.cost.text = quests[position].cost.toString()
    }
}