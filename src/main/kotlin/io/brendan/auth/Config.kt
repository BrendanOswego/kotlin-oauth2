package io.brendan.auth

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.db.DataSourceFactory

class Config : Configuration() {

    @JsonProperty("database")
    val db = DataSourceFactory()

}