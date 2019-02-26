package io.brendan.auth.data.mappers

import io.brendan.auth.data.models.Client
import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import java.sql.ResultSet

class ClientMapper : RowMapper<Client> {

    override fun map(rs: ResultSet, ctx: StatementContext): Client {
        return Client(rs.getString("client_id"), rs.getString("client_secret"))
    }

}