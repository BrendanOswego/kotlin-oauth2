package io.brendan.auth.data.daos

import io.brendan.auth.data.mappers.AuthorizationMapper
import io.brendan.auth.data.models.Authorization
import org.jdbi.v3.sqlobject.config.RegisterRowMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

@RegisterRowMapper(AuthorizationMapper::class)
interface AuthorizationDao {

    @SqlUpdate("insert into authorizations (client_id, code, redirect_uri) " +
            "select :client_id, :code, :redirect_uri where not exists( " +
            "select client_id from authorizations where client_id = :client_id)")
    fun insert(@Bind("client_id") clientId: String,
               @Bind("code") code: String,
               @Bind("redirect_uri") redirectUri: String)

    @SqlQuery("select client_id, code, redirect_uri from authorizations where (client_id = :client_id) limit(1)")
    fun find(@Bind("client_id") clientId: String): Authorization?
}