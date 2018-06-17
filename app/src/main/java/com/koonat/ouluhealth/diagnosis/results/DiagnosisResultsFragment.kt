package com.koonat.ouluhealth.diagnosis.results

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koonat.ouluhealth.R
import com.koonat.ouluhealth.domain.model.Diagnosis
import kotlinx.android.synthetic.main.frame_diagnosis_results.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DiagnosisResultsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frame_diagnosis_results, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(SendDiagnosisEvent())
    }

    override fun onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause()
    }


    private lateinit var viewAdapter: DiagnosisResultsAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDiagnosisReceived(diagnosisEvent: DiagnosisEvent) {
        viewManager = LinearLayoutManager(context)
        viewAdapter = DiagnosisResultsAdapter(searchResults = diagnosisEvent.diagnosisList,
                onClickListener = object : DiagnosisResultsAdapter.OnClickListener {
                    override fun onItemClicked(diagnosis: Diagnosis) {
                        EventBus.getDefault().post(ShowDiseaseDetails(diagnosis))
                    }

                })

        diagnosisRecyclerView.layoutManager = viewManager
        diagnosisRecyclerView.adapter = viewAdapter

        viewAdapter.notifyDataSetChanged()
    }
}

class ShowDiseaseDetails(val diagnosis: Diagnosis)
class DiagnosisEvent(val diagnosisList: List<Diagnosis>)
class SendDiagnosisEvent