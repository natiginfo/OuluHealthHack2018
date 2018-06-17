package com.koonat.ouluhealth.domain.model

import com.google.gson.annotations.SerializedName

data class MatchedSymptom(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("positive") var positive: Boolean = false
)

data class PredictionBody(
        @SerializedName("symptoms") val symptoms: List<MatchedSymptom>,
        @SerializedName("age") val age: Int,
        @SerializedName("sex") val sex: Int,
        @SerializedName("user_id") val userId: String,
        @SerializedName("patient_id") val patientId: String
)

data class PredictedSymptom(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("score") var score: Double,
        @SerializedName("positive") var positive: Boolean = false,
        @SerializedName("skip") var skip: Boolean = false
)

data class DiagnosisBody(
        @SerializedName("symptoms") val symptoms: List<PredictedSymptom>,
        @SerializedName("age") val age: Int,
        @SerializedName("sex") val sex: Int,
        @SerializedName("user_id") val userId: String,
        @SerializedName("patient_id") val patientId: String
)

data class Diagnosis(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("probability") var score: Double
)

data class DiagnosisDetails(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("icd") val icd: String,
        @SerializedName("wiki_url") var wikiUrl: String
)