package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCase
import com.koonat.ouluhealth.domain.model.MatchedSymptom
import com.koonat.ouluhealth.domain.repository.PatientInformationRepository
import com.koonat.ouluhealth.domain.repository.PredictionRepository
import io.reactivex.Single

class GetMatchedSymptomsInteractor(private val patientInfo: PatientInformationRepository,
                                   private val predictionRepo: PredictionRepository)
    : UseCase<String, List<MatchedSymptom>> {
    override fun execute(query: String): Single<List<MatchedSymptom>> {
        return predictionRepo.matchSymptoms(
                patientInfo.patientId,
                patientInfo.userId,
                query)
    }
}