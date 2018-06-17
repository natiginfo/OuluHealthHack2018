package com.koonat.ouluhealth.data

import com.koonat.ouluhealth.domain.repository.HospitalsRepository

object HospitalsRepositoryCreator {
    fun createHospitalsRepository(): HospitalsRepository {
        return MockHospitalsRepository()
    }
}
