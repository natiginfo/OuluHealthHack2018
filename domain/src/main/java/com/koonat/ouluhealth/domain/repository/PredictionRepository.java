package com.koonat.ouluhealth.domain.repository;

import com.koonat.ouluhealth.domain.model.MatchedSymptom;

import java.util.List;

public interface PredictionRepository {
    public List<MatchedSymptom> matchSymptoms(String query);
}
