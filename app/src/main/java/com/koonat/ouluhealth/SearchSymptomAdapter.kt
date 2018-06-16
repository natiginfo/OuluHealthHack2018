package com.koonat.ouluhealth

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.koonat.ouluhealth.domain.model.MatchedSymptom

class SearchSymptomAdapter(private val searchResults: List<MatchedSymptom>,
                           private val onClickListener: OnClickListener) :
        RecyclerView.Adapter<SearchSymptomAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClicked(matchedSymptom: MatchedSymptom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_search_symptom, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = searchResults[position].name
        holder.bind(searchResults[position], onClickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById<TextView>(R.id.symptomNameTextView)

        fun bind(matchedSymptom: MatchedSymptom, onClickListener: OnClickListener) {
            itemView.setOnClickListener {
                onClickListener.onItemClicked(matchedSymptom)
            }
        }
    }
}