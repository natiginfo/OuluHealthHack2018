package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.koonat.ouluhealth.domain.model.PredictedSymptom
import kotlinx.android.synthetic.main.fragment_show_question.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ShowQuestionFragment : Fragment() {

    class ShowQuestionEvent(val total: Double, val left: Double, val questionData: PredictedSymptom)

    class NewQuestionAnsweredEvent(val questionData: PredictedSymptom)

    private lateinit var mView: View
    private lateinit var predictedSymptom: PredictedSymptom

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_show_question, container, false)
        return mView
    }

    override fun onResume() {
        super.onResume()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        yesButton.setOnClickListener {
            predictedSymptom.positive = true
            EventBus.getDefault().post(NewQuestionAnsweredEvent(predictedSymptom))
        }

        noButton.setOnClickListener {
            predictedSymptom.positive = false
            EventBus.getDefault().post(NewQuestionAnsweredEvent(predictedSymptom))
        }

        skipButton.setOnClickListener {
            predictedSymptom.skip = true
            EventBus.getDefault().post(NewQuestionAnsweredEvent(predictedSymptom))
        }

    }

    override fun onPause() {
        EventBus.getDefault().unregister(this)
        super.onPause()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShowQuestionEvent(event: ShowQuestionEvent) {
        Log.d("ShowQuestionEvent", "total: ${event.total}\nleft: ${event.left}")
        // show question data on screen and store temporarily
        predictedSymptom = event.questionData
        questionName.text = event.questionData.name
        questionName.append("?")
        arcPrgress.progress = ((event.left / event.total) * 100).toInt()
    }


}
