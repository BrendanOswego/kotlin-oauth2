package io.brendan.auth.controllers

import io.brendan.auth.data.daos.ClientDao
import io.brendan.auth.data.models.Registration
import io.brendan.auth.utils.ClientCredentials
import org.jdbi.v3.core.Jdbi
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/oauth2/register")
class RegisterController @Inject constructor(
        db: Jdbi
) : BaseController() {

    private val dao = db.onDemand(ClientDao::class.java)

    @GET
    fun index(): Response {
        return Response.status(Response.Status.UNAUTHORIZED).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun register(body: Registration): Response {
        val credentials = ClientCredentials.createClient(body.appName, body.url)

        val clientId = credentials.first
        val clientSecret = credentials.second

        dao.insert(clientId, clientSecret)

        val client = dao.find(clientId)

        return Response.ok(client).build()
    }

}