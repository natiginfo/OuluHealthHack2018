package com.koonat.ouluhealth.diagnosis.hospitals

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.koonat.ouluhealth.ImageLoader
import com.koonat.ouluhealth.ImageLoaderImpl
import com.koonat.ouluhealth.R
import com.koonat.ouluhealth.diagnosis.results.DiagnosisResultsAdapter
import com.koonat.ouluhealth.domain.model.Diagnosis
import com.koonat.ouluhealth.domain.model.Hospital

class HospitalsAdapter(val hospitals: List<Hospital>, private val onClickListener: HospitalsAdapter.OnClickListener) : RecyclerView.Adapter<HospitalsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_hospital, parent, false)
        return HospitalsAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hospitals[position], onClickListener)
    }

    interface OnClickListener {
        fun onItemClicked(clickedHospital: Hospital)
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var hospitalName: TextView = view.findViewById<TextView>(R.id.hospitalNameTextView)
        var hospistalAddress: TextView = view.findViewById<TextView>(R.id.hospitalAddress)
        var hospitalLogo: ImageView = view.findViewById<ImageView>(R.id.hospitalLogoImageView)

        fun bind(hospital: Hospital, onClickListener: HospitalsAdapter.OnClickListener) {
            val loader: ImageLoader = ImageLoaderImpl()
            loader.loadImageInto(hospital.logoUrl, hospitalLogo)
            hospitalName.text = hospital.name
            hospistalAddress.text = hospital.address
            itemView.setOnClickListener {
                onClickListener.onItemClicked(hospital)
            }
        }
    }
}
