package com.koonat.ouluhealth.diagnosis.hospitals

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koonat.ouluhealth.R
import com.koonat.ouluhealth.diagnosis.results.SendDiagnosisEvent
import com.koonat.ouluhealth.domain.model.Hospital
import kotlinx.android.synthetic.main.frame_diagnosis_results.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HospitalsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_hospital, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(SendHospitalsEvent())
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause()
    }

    private lateinit var viewAdapter: HospitalsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDiagnosisReceived(hospitalEvent: HospitalEvent) {
        Log.d("HOSPITALS", "${hospitalEvent.hospitals.size}")
        viewManager = LinearLayoutManager(context)
        viewAdapter = HospitalsAdapter(hospitals = hospitalEvent.hospitals,
                onClickListener = object : HospitalsAdapter.OnClickListener {
                    override fun onItemClicked(hospital: Hospital) {
                        EventBus.getDefault().post(ShowHospitalBooking(hospital))
                    }

                })

        diagnosisRecyclerView.layoutManager = viewManager
        diagnosisRecyclerView.adapter = viewAdapter

        viewAdapter.notifyDataSetChanged()
    }
}

class SendHospitalsEvent
class ShowHospitalBooking(val hospital: Hospital)
class HospitalEvent(val hospitals: List<Hospital>)
