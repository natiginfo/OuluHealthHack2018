package com.koonat.ouluhealth.data

import com.koonat.ouluhealth.domain.model.Hospital
import com.koonat.ouluhealth.domain.repository.HospitalsRepository
import io.reactivex.Single
import java.util.*

class MockHospitalsRepository : HospitalsRepository {
    override fun getHospitals(): Single<List<Hospital>> {
        return Single.create { emitter ->
            val hospitalsFromLocalData = ArrayList<Hospital>()
            val hospital1 = Hospital(
                    name = "Haukipudas health centre",
                    address = "Simppulantie 15, Oulu",
                    logoUrl = "https://cdn.dribbble.com/users/22251/screenshots/803201/no-photo-grey.png",
                    appointmentUrl = ""
            )
            val hospital2 = Hospital(
                    name = "Mehiläinen Oulu",
                    address = "Kauppurienkatu 9, 90100 Oulu",
                    logoUrl = "https://upload.wikimedia.org/wikipedia/fi/thumb/9/9f/Mehil%C3%A4isen_logo.svg/1200px-Mehil%C3%A4isen_logo.svg.png",
                    appointmentUrl = "https://ajanvaraus.mehilainen.fi/"
            )

            val hospital3 = Hospital(
                    name = "Terveystalo Oulu",
                    address = "Biologintie 5-7, 90580 Oulu",
                    logoUrl = "http://fcinter.fi/media/filer_public_thumbnails/filer_public/21/13/211366f4-d13e-40d3-8981-28bf38051a45/terveystalo_kuva_.jpg__1175x725_q95_box-0%2C29%2C1200%2C769_crop_detail_subsampling-2.jpg",
                    appointmentUrl = "https://ajanvaraus.terveystalo.com/en/"
            )

            hospitalsFromLocalData.add(hospital1)
            hospitalsFromLocalData.add(hospital2)
            hospitalsFromLocalData.add(hospital3)

            emitter.onSuccess(hospitalsFromLocalData)
        }
    }
}
