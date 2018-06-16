package com.koonat.ouluhealth.domain.model

import com.google.gson.annotations.SerializedName

data class TokenHolder(@SerializedName("token") val token: String)