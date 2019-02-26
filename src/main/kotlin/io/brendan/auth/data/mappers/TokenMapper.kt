package io.brendan.auth.data.mappers

import io.brendan.auth.data.models.Token
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class TokenMapper : RowMapper<Token> {

    override fun map(rs: ResultSet, ctx: StatementContext): Token {
        return Token(
                rs.getString("access_token"),
                rs.getString("token_type"),
                rs.getInt("expires_in"),
                rs.getString("refresh_token"),
                rs.getString("client_id"),
                rs.getLong("expires"),
                rs.getLong("issued")

        )
    }

}