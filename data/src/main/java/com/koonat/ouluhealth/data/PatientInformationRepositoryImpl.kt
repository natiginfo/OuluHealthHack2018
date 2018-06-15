package com.koonat.ouluhealth.data

import com.koonat.ouluhealth.domain.repository.PatientInformationRepository
import com.koonat.ouluhealth.domain.repository.StorageService

class PatientInformationRepositoryImpl(private val storageService: StorageService) : PatientInformationRepository {
    override fun getPatientId(): String {
        return storageService.readString("patient_id", "")
    }

    override fun getUserId(): String {
        return storageService.readString("user_id", "")
    }

    override fun getAge(): Int {
        return storageService.readInt("age", 0)
    }

    override fun getSex(): Int {
        return storageService.readInt("sex", 0)
    }
}