package io.brendan.auth.controllers

import io.brendan.auth.data.daos.AuthorizationDao
import io.brendan.auth.data.daos.ClientDao
import io.brendan.auth.utils.ClientCredentials
import org.jdbi.v3.core.Jdbi
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.UriBuilder

@Path("/oauth2/authorize")
class AuthorizeController @Inject constructor(
        db: Jdbi
) : BaseController() {

    private val logger = LoggerFactory.getLogger(AuthorizeController::class.java)
    private val clientDao = db.onDemand(ClientDao::class.java)
    private val authorizationDao = db.onDemand(AuthorizationDao::class.java)

    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    fun authorize(@QueryParam("response_type") responseType: String?, @QueryParam("client_id") clientId: String,
                  @QueryParam("redirect_uri") redirectUri: String, @QueryParam("scope") scope: String?,
                  @QueryParam("state") state: String?): Response {

        val client = clientDao.find(clientId) ?: return Response.status(Response.Status.NOT_FOUND).build()

        val code = ClientCredentials.createAuthorizationCode(client.clientId)
                ?: return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()

        authorizationDao.insert(client.clientId, code, redirectUri)

        authorizationDao.find(client.clientId) ?: return Response.status(Response.Status.CONFLICT).build()

        val uri = UriBuilder.fromUri("$redirectUri?code=$code").build()

        return Response.status(Response.Status.FOUND).location(uri).build()
    }

    @GET
    @Path("{client_id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getCode(@PathParam("client_id") clientId: String): Response {
        authorizationDao.find(clientId) ?: return Response.status(Response.Status.NOT_FOUND).build()
        logger.info("found client")
        return Response.status(Response.Status.OK).build()
    }

}