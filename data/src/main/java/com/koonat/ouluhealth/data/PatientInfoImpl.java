package com.koonat.ouluhealth.data;

import com.koonat.ouluhealth.domain.repository.PatientInformationRepository;

import java.util.UUID;

public class PatientInfoImpl implements PatientInformationRepository {

    private static volatile PatientInfoImpl mInstance;
    private String patientId;
    private int sex;
    private int age;

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
        return sex;
    }

    @Override
    public int getSex() {
        return age;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
