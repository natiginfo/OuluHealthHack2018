package com.koonat.ouluhealth.domain.model

import com.google.gson.annotations.SerializedName

data class MatchedSymptom(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("positive") var positive: Boolean = false
)

data class PredictedSymptom(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("score") var score: Int,
        @SerializedName("positive") var positive: Boolean = false,
        @SerializedName("skip") var skip: Boolean = false
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