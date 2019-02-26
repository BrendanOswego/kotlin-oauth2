package io.brendan.auth.controllers

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Response

@Path("/oauth2/redirect")
class RedirectController @Inject constructor(

) : BaseController() {

    @GET
    @Path("")
    fun index(@QueryParam("code") code: String?): Response {
        return Response.ok().build()
    }

}