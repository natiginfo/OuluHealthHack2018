package com.koonat.ouluhealth

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.koonat.ouluhealth.data.AccessTokenRepositoryCreator
import com.koonat.ouluhealth.data.PredictionRepositoryCreator
import com.koonat.ouluhealth.domain.interactor.GetAccessTokenInteractor
import com.koonat.ouluhealth.domain.interactor.GetMatchedSymptomsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers


class SearchSymptomActivity : AppCompatActivity() {

    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_symptom)
        val accessTokenRepo = AccessTokenRepositoryCreator.createAccessTokenRepository()
        val getAccessTokenInteractor = GetAccessTokenInteractor(accessTokenRepo = accessTokenRepo)

        getAccessTokenInteractor.execute()
                .map { tokenHolder ->
                    token = tokenHolder.token
                    return@map PredictionRepositoryCreator.createPredictionRepository(tokenHolder.token)
                }
                .map { predictionRepo ->
                    GetMatchedSymptomsInteractor(
                            patientInfo = PatientInfoImpl.getInstance(),
                            predictionRepo = predictionRepo)
                }
                .flatMap { interactor -> interactor.execute("vagina") }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy {
                    Log.d("MainAct", "Item: " + it.toString())
                }
    }
}
