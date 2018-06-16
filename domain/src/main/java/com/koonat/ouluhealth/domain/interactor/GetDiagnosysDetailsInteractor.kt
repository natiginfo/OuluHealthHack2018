package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCase
import com.koonat.ouluhealth.domain.model.DiagnosisDetails
import com.koonat.ouluhealth.domain.repository.PatientInformationRepository
import com.koonat.ouluhealth.domain.repository.PredictionRepository
import io.reactivex.Single

class GetDiagnosysDetailsInteractor(private val patientInfo: PatientInformationRepository,
                                    private val predictionRepo: PredictionRepository)
    : UseCase<Int, DiagnosisDetails> {
    override fun execute(id: Int): Single<DiagnosisDetails> {
        return predictionRepo.getDiagnosisDetails(id, patientInfo.patientId, patientInfo.userId)
    }

}
