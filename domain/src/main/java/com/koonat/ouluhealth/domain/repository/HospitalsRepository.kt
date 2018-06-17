package com.koonat.ouluhealth.domain.repository

import com.koonat.ouluhealth.domain.model.Hospital
import io.reactivex.Single

interface HospitalsRepository {
     fun getHospitals(): Single<List<Hospital>>
}