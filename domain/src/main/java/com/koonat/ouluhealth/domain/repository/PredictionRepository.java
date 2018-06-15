package com.koonat.ouluhealth.domain.repository;

import com.koonat.ouluhealth.domain.CallbackWithResult;
import com.koonat.ouluhealth.domain.model.Diagnosis;
import com.koonat.ouluhealth.domain.model.DiagnosisDetails;
import com.koonat.ouluhealth.domain.model.MatchedSymptom;
import com.koonat.ouluhealth.domain.model.PredictedSymptom;

import java.util.List;

public interface PredictionRepository {
    public void matchSymptoms(String query,
                              CallbackWithResult<List<MatchedSymptom>> callback);

    public void predictSymptoms(List<MatchedSymptom> selectedSymptoms,
                                CallbackWithResult<List<PredictedSymptom>> callback);

    public void diagnose(List<PredictedSymptom> selectedSymptoms,
                         CallbackWithResult<List<Diagnosis>> callback);

    public void getDiagnosisDetails(int id,
                                    CallbackWithResult<DiagnosisDetails> callback);
}