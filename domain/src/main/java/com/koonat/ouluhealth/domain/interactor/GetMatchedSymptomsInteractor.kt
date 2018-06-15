package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.CallbackWithResult
import com.koonat.ouluhealth.domain.UseCaseWithParameter
import com.koonat.ouluhealth.domain.model.MatchedSymptom
import com.koonat.ouluhealth.domain.repository.PredictionRepository

class GetMatchedSymptomsInteractor(private val predictionRepository: PredictionRepository)
    : UseCaseWithParameter<String, List<MatchedSymptom>> {

    /**
     * @param parameter query to search
     * @param callback callback for observing error or success cases
     */
    override fun execute(parameter: String,
                         callback: UseCaseWithParameter.Callback<List<MatchedSymptom>>) {
        predictionRepository.matchSymptoms(parameter, object : CallbackWithResult<List<MatchedSymptom>> {
            override fun onSuccess(result: List<MatchedSymptom>) {
                callback.onSuccess(result)
            }

            override fun onError(exception: Exception) {
                callback.onError(exception)
            }
        })
    }
}