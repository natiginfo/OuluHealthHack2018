package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_symptoms.*
import org.greenrobot.eventbus.EventBus

class AddSymptomsFragment : Fragment() {
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_add_symptoms, container, false)
        return mView
    }

    override fun onResume() {
        super.onResume()
        symptomEditText.setOnClickListener {
            EventBus.getDefault().post(SymptomEditClicked())
        }
    }
}

class SymptomEditClicked {}
