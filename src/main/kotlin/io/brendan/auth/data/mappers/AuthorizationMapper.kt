package io.brendan.auth.data.mappers

import io.brendan.auth.data.models.Authorization
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class AuthorizationMapper : RowMapper<Authorization> {

    override fun map(rs: ResultSet, ctx: StatementContext): Authorization {
        return Authorization(rs.getString("client_id"), rs.getString("code"), rs.getString("redirect_uri"))
    }

}