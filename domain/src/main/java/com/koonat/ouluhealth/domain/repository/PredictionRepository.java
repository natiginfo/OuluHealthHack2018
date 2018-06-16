package com.koonat.ouluhealth.domain.repository;

import com.koonat.ouluhealth.domain.model.Diagnosis;
import com.koonat.ouluhealth.domain.model.DiagnosisBody;
import com.koonat.ouluhealth.domain.model.DiagnosisDetails;
import com.koonat.ouluhealth.domain.model.MatchedSymptom;
import com.koonat.ouluhealth.domain.model.PredictedSymptom;
import com.koonat.ouluhealth.domain.model.PredictionBody;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * TODO: do not forget to add auth token when creating client
 */
public interface PredictionRepository {
    @GET("/symptoms/match")
    public Single<List<MatchedSymptom>> matchSymptoms(
            @Query("patient_id") String patientId,
            @Query("user_id") String userId,
            @Query("query") String query
    );

    @Headers({
            "Content-Type: application/json",
    })
    @POST("/symptoms/predict")
    public Single<List<PredictedSymptom>> predictSymptoms(
            @Body PredictionBody predictionBody
    );

    @Headers({
            "Content-Type: application/json",
    })
    @POST("/diagnoses/rank")
    public Single<List<Diagnosis>> diagnose(@Body DiagnosisBody diagnosisBody);

    @GET("/diagnosis/{id}")
    public Single<DiagnosisDetails> getDiagnosisDetails(@Path("id") int id,
                                                        @Query("patient_id") String patientId,
                                                        @Query("user_id") String userId);
}