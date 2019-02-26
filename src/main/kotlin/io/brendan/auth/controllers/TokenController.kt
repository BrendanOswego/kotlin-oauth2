package io.brendan.auth.controllers

import io.brendan.auth.data.daos.AuthorizationDao
import io.brendan.auth.data.daos.ClientDao
import io.brendan.auth.data.daos.TokenDao
import io.brendan.auth.utils.ClientCredentials
import org.jdbi.v3.core.Jdbi
import java.time.Instant
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/oauth2/token")
class TokenController @Inject constructor(
        private val db: Jdbi
) : BaseController() {

    private val clientDao = db.onDemand(ClientDao::class.java)
    private val authorizationDao = db.onDemand(AuthorizationDao::class.java)
    private val tokenDao = db.onDemand(TokenDao::class.java)

    @GET
    fun index(): Response {
        return Response.status(Response.Status.UNAUTHORIZED).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    fun post(@FormParam("grant_type") grantType: String, @FormParam("code") code: String,
             @FormParam("redirect_uri") redirectUri: String, @FormParam("client_id") clientId: String,
             @FormParam("refresh_token") refreshToken: String?): Response {

        var accessToken: String
        var refreshTokenTemp: String

        val client = clientDao.find(clientId) ?: return Response.noContent().build()

        accessToken = ClientCredentials.accessToken(client.clientId, code, redirectUri)
                ?: return Response.serverError().build()

        refreshTokenTemp = ClientCredentials.refreshToken(client.clientSecret, code, redirectUri)
                ?: return Response.serverError().build()

        val expiresIn = 3600
        val tokenType = "Bearer"

        val issued = Instant.now().epochSecond
        val expires = issued + expiresIn

        tokenDao.insert(accessToken, tokenType, expiresIn, refreshTokenTemp, client.clientId, issued, expires)

        val token = tokenDao.find(accessToken) ?: return Response.status(Response.Status.CONFLICT).build()

        return Response.ok(token).build()
    }

}

