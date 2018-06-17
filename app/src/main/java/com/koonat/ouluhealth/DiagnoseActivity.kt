package com.koonat.ouluhealth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.Gson
import com.koonat.ouluhealth.data.AccessTokenRepositoryCreator
import com.koonat.ouluhealth.data.HospitalsRepositoryCreator
import com.koonat.ouluhealth.data.PatientInfoImpl
import com.koonat.ouluhealth.data.PredictionRepositoryCreator
import com.koonat.ouluhealth.diagnosis.hospitals.HospitalEvent
import com.koonat.ouluhealth.diagnosis.hospitals.HospitalsFragment
import com.koonat.ouluhealth.diagnosis.hospitals.SendHospitalsEvent
import com.koonat.ouluhealth.diagnosis.hospitals.ShowHospitalBooking
import com.koonat.ouluhealth.diagnosis.results.DiagnosisEvent
import com.koonat.ouluhealth.diagnosis.results.DiagnosisResultsFragment
import com.koonat.ouluhealth.diagnosis.results.SendDiagnosisEvent
import com.koonat.ouluhealth.diagnosis.results.ShowDiseaseDetails
import com.koonat.ouluhealth.domain.interactor.*
import com.koonat.ouluhealth.domain.model.Diagnosis
import com.koonat.ouluhealth.domain.model.Hospital
import com.koonat.ouluhealth.domain.model.MatchedSymptom
import com.koonat.ouluhealth.domain.model.PredictedSymptom
import com.koonat.ouluhealth.search.SearchSymptomActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DiagnoseActivity : AppCompatActivity() {
    companion object {
        val TAG = "DiagnoseActivity"
        val BACK_USER_DETAILS = "BACK_USER_DETAILS"
        val BACK_ADD_SYMPTOMS = "BACK_ADD_SYMPTOMS"
        val BACK_DIAGNOSIS_RESULTS = "BACK_DIAGNOSIS_RESULTS"
        val BACK_DIAGNOSIS_DETAILS = "BACK_DIAGNOSIS_DETAILS"
        val PICK_SYMPTOM_REQUEST = 111
        val SHOW_URL_REQUEST = 112
    }

    private var age: Int = 0
    private var sex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnose)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction
                .add(R.id.customActionBarHolder,
                        CustomActionBar.getInstance(title = "Easy diagnosis",
                                description = "For any health problems you encounter"))
                .add(R.id.contentHolder, UserDetailsFragment())
                // we do not want to go white view, so, backstack is empty for first fragments
                .commit()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showSymptomSelectorFragment(event: ContinueButtonClickedEvent) {
        age = event.age
        sex = event.sex
        PatientInfoImpl.getInstance().age = age
        PatientInfoImpl.getInstance().sex = sex
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction
                .add(R.id.customActionBarHolder,
                        CustomActionBar.getInstance(title = "What are your symptoms?",
                                description = "Give details about your problem"))
                .add(R.id.contentHolder, AddSymptomsFragment())
                .addToBackStack(BACK_ADD_SYMPTOMS)
                // we do not want to go white view, so, backstack is empty for first fragments
                .commit()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showSearchSymptomActivity(symptomEditClicked: SymptomEditClicked) {
        val i = Intent(this, SearchSymptomActivity::class.java)
        startActivityForResult(i, PICK_SYMPTOM_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_SYMPTOM_REQUEST) {
            val symptom = data?.getStringExtra("symptom")
            val matchedSymptom = Gson().fromJson<MatchedSymptom>(symptom, MatchedSymptom::class.java)
            matchedSymptom.positive = true
            showAdditionalQuestions(matchedSymptom)
        } else if (requestCode == SHOW_URL_REQUEST) {
            AlertDialog.Builder(this)
                    .setTitle("It'll be rainy...")
                    .setMessage("It seems like that day will be rainy. Would you like to see transportation options?")
                    .setPositiveButton("Book taxi", null)
                    .setNegativeButton("No, thanks", null)
                    .create().show()
        }
    }

    private var token = ""
    private fun showAdditionalQuestions(matchedSymptom: MatchedSymptom?) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction
                .add(R.id.customActionBarHolder,
                        CustomActionBar.getInstance(description = "Additional questions",
                                title = "Do you also have"))
                .add(R.id.contentHolder, ShowQuestionFragment())
                .commit()

        val accessTokenRepo = AccessTokenRepositoryCreator.createAccessTokenRepository()
        val getAccessTokenInteractor = GetAccessTokenInteractor(accessTokenRepo = accessTokenRepo)

        getAccessTokenInteractor.execute()
                .map { tokenHolder ->
                    token = tokenHolder.token
                    return@map PredictionRepositoryCreator.createPredictionRepository(tokenHolder.token)
                }
                .map { predictionRepo ->
                    GetPredictedSymptomsInteractor(
                            patientInfo = PatientInfoImpl.getInstance(),
                            predictionRepo = predictionRepo)
                }
                .flatMap { interactor -> interactor.execute(listOf(matchedSymptom!!)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { predictedSymptoms ->
                    Log.d(TAG, "QUESTIONS RECEIVED " + predictedSymptoms.toString())
                    printQuestions(predictedSymptoms)
                }

    }

    private lateinit var predictedSymptoms: List<PredictedSymptom>
    private var lastQuestionIndex = 0
    private fun printQuestions(predictedSymptoms: List<PredictedSymptom>?) {
        this.predictedSymptoms = predictedSymptoms!!
        showNextQuestion()
    }

    var answeredQuestions: MutableList<PredictedSymptom> = ArrayList()
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNewQuestionAnsweredEvent(event: ShowQuestionFragment.NewQuestionAnsweredEvent) {
        answeredQuestions.add(event.questionData)
        showNextQuestion()
    }

    private lateinit var diagnosis: List<Diagnosis>
    private fun showNextQuestion() {
        if (lastQuestionIndex < predictedSymptoms.size) {
            EventBus.getDefault().post(
                    ShowQuestionFragment.ShowQuestionEvent(predictedSymptoms.size.toDouble(),
                            (lastQuestionIndex).toDouble(),
                            predictedSymptoms[lastQuestionIndex]
                    ))
            lastQuestionIndex++
        } else {
            //show progressbar and analyze
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction
                    .add(R.id.customActionBarHolder,
                            CustomActionBar.getInstance(description = "Please, wait",
                                    title = "Analyzing"))
                    .replace(R.id.contentHolder, ProgressFragment())
                    .disallowAddToBackStack()
                    .commit()

            val accessTokenRepo = AccessTokenRepositoryCreator.createAccessTokenRepository()
            val getAccessTokenInteractor = GetAccessTokenInteractor(accessTokenRepo = accessTokenRepo)

            getAccessTokenInteractor.execute()
                    .map { tokenHolder ->
                        token = tokenHolder.token
                        return@map PredictionRepositoryCreator.createPredictionRepository(tokenHolder.token)
                    }
                    .map { predictionRepo ->
                        GetDiagnosisInteractor(
                                patientInfo = PatientInfoImpl.getInstance(),
                                predictionRepo = predictionRepo)
                    }
                    .flatMap { interactor -> interactor.execute(answeredQuestions) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeBy { diagnosisList ->
                        Log.d(TAG, "DIAGNOSYS RECEIVED " + diagnosisList.toString())
                        diagnosis = diagnosisList
                        val fragmentManager1 = supportFragmentManager
                        val fragmentTransaction1 = fragmentManager1.beginTransaction()
                        fragmentTransaction1
                                .add(R.id.customActionBarHolder,
                                        CustomActionBar.getInstance(title = "Diagnosis",
                                                description = "Possible result according to provided information"))
                                .replace(R.id.contentHolder, DiagnosisResultsFragment())
                                .addToBackStack(BACK_DIAGNOSIS_RESULTS)
                                // we do not want to go white view, so, backstack is empty for first fragments
                                .commit()
                    }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFragmentReady(event: SendDiagnosisEvent) {
        EventBus.getDefault().post(DiagnosisEvent(diagnosis))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showDiseaseDetails(event: ShowDiseaseDetails) {
        val accessTokenRepo = AccessTokenRepositoryCreator.createAccessTokenRepository()
        val getAccessTokenInteractor = GetAccessTokenInteractor(accessTokenRepo = accessTokenRepo)

        getAccessTokenInteractor.execute()
                .map { tokenHolder ->
                    token = tokenHolder.token
                    return@map PredictionRepositoryCreator.createPredictionRepository(tokenHolder.token)
                }
                .map { predictionRepo ->
                    GetDiagnosysDetailsInteractor(
                            patientInfo = PatientInfoImpl.getInstance(),
                            predictionRepo = predictionRepo)
                }
                .flatMap { interactor -> interactor.execute(event.diagnosis.id) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { diagnosisDetails ->
                    Log.d(TAG, "DETAILS RECEIVED " + diagnosisDetails.toString())
                    val details = diagnosisDetails

                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.add(R.id.customActionBarHolder,
                            CustomActionBar.getInstance(title = "Treatment suggestion",
                                    description = "One more thing!"))
                            .replace(R.id.contentHolder, DiagnosisDetailsFragment.getInstance(details.name, details.description, details.wikiUrl))
                            .addToBackStack(BACK_DIAGNOSIS_DETAILS)
                            .commit()
                }
    }


    private lateinit var receivedHospitals: List<Hospital>
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showBookingOptions(event: ShowBookingOptionsEvent) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        GetHospitalsInteractor(HospitalsRepositoryCreator.createHospitalsRepository())
                .execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    receivedHospitals = it
                    fragmentTransaction.add(R.id.customActionBarHolder,
                            CustomActionBar.getInstance(title = "Choose hospital",
                                    description = "Where do you prefer to go?"))
                            .replace(R.id.contentHolder, HospitalsFragment())
                            .commit()
                }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFragmentReady(event: SendHospitalsEvent) {
        EventBus.getDefault().post(HospitalEvent(receivedHospitals))
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFragmentReady(event: ShowHospitalBooking) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(event.hospital.appointmentUrl))
        startActivityForResult(browserIntent, SHOW_URL_REQUEST)
    }


}