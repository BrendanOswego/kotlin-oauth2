package io.brendan.auth.data.models

import com.squareup.moshi.Json

data class Registration(
        @Json(name = "app_name") val appName: String,
        @Json(name = "redirect_uri") val url: String
)