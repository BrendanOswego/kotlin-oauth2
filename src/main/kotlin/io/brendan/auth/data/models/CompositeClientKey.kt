package io.brendan.auth.data.models

import javax.persistence.Id

data class CompositeClientKey(
        @Id val clientId: String,
        @Id val clientSecret: String
)