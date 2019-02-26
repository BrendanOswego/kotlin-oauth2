package io.brendan.auth.controllers

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("")
@Produces(MediaType.TEXT_PLAIN)
class IndexController @Inject constructor() : BaseController() {

    @GET
    fun index(): String {
        return "api running"
    }

}
