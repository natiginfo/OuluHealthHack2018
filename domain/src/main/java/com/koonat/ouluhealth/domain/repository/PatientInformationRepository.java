package com.koonat.ouluhealth.domain.repository;

public interface PatientInformationRepository {
    public String getPatientId();

    public String getUserId();

    public int getAge();

    public int getSex();
}
