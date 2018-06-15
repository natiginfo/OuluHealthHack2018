package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCaseWithParameter
import com.koonat.ouluhealth.domain.model.MatchedSymptom
import com.koonat.ouluhealth.domain.repository.PredictionRepository

class GetMatchedSymptomsInteractor(val predictionRepository: PredictionRepository)
    : UseCaseWithParameter<String, List<MatchedSymptom>> {

    override fun execute(query: String,
                         callback: UseCaseWithParameter.Callback<List<MatchedSymptom>>) {
    }
}