package com.koonat.ouluhealth.domain.interactor

import com.koonat.ouluhealth.domain.UseCaseWithoutParameter
import com.koonat.ouluhealth.domain.model.Hospital
import com.koonat.ouluhealth.domain.repository.HospitalsRepository

import io.reactivex.Single

class GetHospitalsInteractor(private val hospitalRepo: HospitalsRepository) : UseCaseWithoutParameter<List<Hospital>> {
    override fun execute(): Single<List<Hospital>> {
        return hospitalRepo.getHospitals()
    }
}
