package com.koonat.ouluhealth;

import com.koonat.ouluhealth.domain.repository.PatientInformationRepository;

import java.util.UUID;

public class PatientInfoImpl implements PatientInformationRepository {

    private static volatile PatientInfoImpl mInstance;
    private String patientId;

    private PatientInfoImpl() {
        patientId = UUID.randomUUID().toString();
    }

    public static PatientInfoImpl getInstance() {
        if (mInstance == null) {
            synchronized (PatientInfoImpl.class) {
                if (mInstance == null) {
                    mInstance = new PatientInfoImpl();
                }
            }
        }
        return mInstance;
    }

    @Override
    public String getPatientId() {
        return patientId;
    }

    @Override
    public String getUserId() {
        return BuildConfig.USER_ID;
    }

    @Override
    public int getAge() {
        return 24;
    }

    @Override
    public int getSex() {
        return 0;
    }
}
