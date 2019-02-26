package io.brendan.auth.data.daos

import io.brendan.auth.data.mappers.TokenMapper
import io.brendan.auth.data.models.Token
import org.jdbi.v3.sqlobject.config.RegisterRowMapper
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

@RegisterRowMapper(TokenMapper::class)
interface TokenDao {

    @SqlUpdate("insert into tokens (access_token, token_type, expires_in, refresh_token, client_id, expires, issued) " +
            "select :access_token, :token_type, :expires_in, :refresh_token, :client_id, :expires, :issued " +
            "where not exists (select access_token from tokens where access_token = :access_token)")
    fun insert(@Bind("access_token") accessToken: String, @Bind("token_type") tokenType: String,
               @Bind("expires_in") expiresIn: Int, @Bind("refresh_token") refreshToken: String,
               @Bind("client_id") clientId: String, @Bind("expires") expires: Long, @Bind("issued") issued: Long)

    @SqlQuery("select * from tokens where (access_token = :access_token)")
    fun find(@Bind("access_token") accessToken: String): Token?

}