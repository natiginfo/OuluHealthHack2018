package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_details.*

class UserDetailsFragment : Fragment() {
    private var selectedGender = -1
    private lateinit var mView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_user_details, container, false)
        return mView
    }

    override fun onResume() {
        super.onResume()
        maleToggleButton.setOnCheckedChangeListener { view, isChecked ->
            femaleToggleButton.isClickable = !isChecked
            if (isChecked) {
                selectedGender = 1
            }
        }

        femaleToggleButton.setOnCheckedChangeListener { view, isChecked ->
            maleToggleButton.isClickable = !isChecked
            if (isChecked) {
                selectedGender = 0
            }
        }
    }
}