package io.brendan.auth.data.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "authorizations")
data class Authorization(
        @Id @Column(name = "client_id") val clientId: String,
        val code: String,
        @Column(name = "redirect_uri") val redirectUri: String
)