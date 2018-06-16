package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCase
import com.koonat.ouluhealth.domain.model.MatchedSymptom
import com.koonat.ouluhealth.domain.model.PredictedSymptom
import com.koonat.ouluhealth.domain.model.PredictionBody
import com.koonat.ouluhealth.domain.repository.PatientInformationRepository
import com.koonat.ouluhealth.domain.repository.PredictionRepository
import io.reactivex.Single

class GetPredictedSymptomsInteractor(private val patientInfo: PatientInformationRepository,
                                     private val predictionRepo: PredictionRepository)
    : UseCase<List<MatchedSymptom>, List<PredictedSymptom>> {
    override fun execute(selectedSymptoms: List<MatchedSymptom>): Single<List<PredictedSymptom>> {
        var predictionBody: PredictionBody = PredictionBody(
                symptoms = selectedSymptoms,
                age = patientInfo.age,
                sex = patientInfo.sex,
                userId = patientInfo.userId,
                patientId = patientInfo.patientId
        )

        return predictionRepo.predictSymptoms(predictionBody)
    }

}
