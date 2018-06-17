package com.koonat.ouluhealth.diagnosis.results

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.koonat.ouluhealth.R
import com.koonat.ouluhealth.domain.model.Diagnosis

class DiagnosisResultsAdapter(private val searchResults: List<Diagnosis>,
                       private val onClickListener: OnClickListener) :
        RecyclerView.Adapter<DiagnosisResultsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClicked(matchedSymptom: Diagnosis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_disease_name, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = searchResults[position].name
        holder.bind(searchResults[position], onClickListener)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById<TextView>(R.id.symptomNameTextView)

        fun bind(matchedSymptom: Diagnosis, onClickListener: OnClickListener) {
            itemView.setOnClickListener {
                onClickListener.onItemClicked(matchedSymptom)
            }
        }
    }
}