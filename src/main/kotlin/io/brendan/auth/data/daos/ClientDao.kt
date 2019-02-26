package io.brendan.auth.data.daos

import io.brendan.auth.data.mappers.ClientMapper
import io.brendan.auth.data.models.Client
import org.jdbi.v3.sqlobject.config.RegisterRowMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

@RegisterRowMapper(ClientMapper::class)
interface ClientDao {

    @SqlUpdate("insert into clients (client_id, client_secret) " +
            "select :client_id, :client_secret " +
            "where not exists(select client_id from clients where client_id = :client_id)")
    fun insert(@Bind("client_id") clientId: String,
               @Bind("client_secret") clientSecret: String)

    @SqlQuery("select client_id, client_secret from clients where (client_id = :client_id)")
    fun find(@Bind("client_id") clientId: String): Client?
}