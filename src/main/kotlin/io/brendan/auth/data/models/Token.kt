package io.brendan.auth.data.models

import com.squareup.moshi.Json
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tokens")
data class Token(
        @Id @Json(name = "access_token") @Column(name = "access_token") val accessToken: String,
        @Json(name = "token_type") @Column(name = "token_type") val tokenType: String? = "Bearer",
        @Json(name = "expires_in") @Column(name = "expires_in") val expiresIn: Int,
        @Json(name = "refresh_token") @Column(name = "refresh_token") val refreshToken: String,
        @Json(name = "client_id") @Column(name = "client_id") val clientId: String,
        val expires: Long,
        val issued: Long
)