package io.brendan.auth.data.models

import javax.persistence.*

@Entity
@Table(name = "clients")
@IdClass(CompositeClientKey::class)
data class Client(
        @Id @Column(name = "client_id") val clientId: String,
        @Id @Column(name = "client_secret") val clientSecret: String
)