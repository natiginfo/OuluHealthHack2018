package com.koonat.ouluhealth.search

import com.koonat.ouluhealth.PatientInfoImpl
import com.koonat.ouluhealth.data.AccessTokenRepositoryCreator
import com.koonat.ouluhealth.data.PredictionRepositoryCreator
import com.koonat.ouluhealth.domain.interactor.GetAccessTokenInteractor
import com.koonat.ouluhealth.domain.interactor.GetMatchedSymptomsInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class SearchSymptomPresenter(val view: SearchSymptomContract.View) : SearchSymptomContract.Presenter {
    private var token = ""

    override fun getSuggestions(query: String) {
        view.onShowProgressbar()
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
                .flatMap { interactor -> interactor.execute(query) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { matchedSymptoms ->
                    view.onRemoveProgressbar()
                    view.onSuggestionsLoaded(matchedSymptoms)
                }
    }
}
