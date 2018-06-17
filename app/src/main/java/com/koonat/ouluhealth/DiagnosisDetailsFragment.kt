package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class DiagnosisDetailsFragment : Fragment() {
    companion object {
        private val EXTRA_TITLE = "EXTRA_TITLE"
        private val EXTRA_DETAILS = "EXTRA_DETAILS"
        private val EXTRA_WIKI_URL = "EXTRA_WIKI_URL"
        fun getInstance(title: String, details: String, wikiUrl: String): DiagnosisDetailsFragment {
            val fragment = DiagnosisDetailsFragment()
            val args = Bundle()
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_DETAILS, details)
            args.putString(EXTRA_WIKI_URL, wikiUrl)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_diagnosis_details, container, false)
    }

    override fun onResume() {
        super.onResume()
        val args = arguments
    }
}