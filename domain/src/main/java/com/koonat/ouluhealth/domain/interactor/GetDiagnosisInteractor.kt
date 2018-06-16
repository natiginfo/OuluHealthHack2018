package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCase
import com.koonat.ouluhealth.domain.model.Diagnosis
import com.koonat.ouluhealth.domain.model.DiagnosisBody
import com.koonat.ouluhealth.domain.model.PredictedSymptom
import com.koonat.ouluhealth.domain.repository.PatientInformationRepository
import com.koonat.ouluhealth.domain.repository.PredictionRepository
import io.reactivex.Single

class GetDiagnosisInteractor(private val patientInfo: PatientInformationRepository,
                             private val predictionRepo: PredictionRepository)
    : UseCase<List<PredictedSymptom>, List<Diagnosis>> {
    override fun execute(selectedSymptoms: List<PredictedSymptom>): Single<List<Diagnosis>> {
        var diagnosisBody: DiagnosisBody = DiagnosisBody(
                symptoms = selectedSymptoms,
                age = patientInfo.age,
                sex = patientInfo.sex,
                userId = patientInfo.userId,
                patientId = patientInfo.patientId
        )

        return predictionRepo.diagnose(diagnosisBody)
    }

}
