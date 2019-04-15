package org.m2n.webapplications2.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldController {

    @GET
    public Response helloWorld() {
        return Response.ok()
            .entity("Hello World!")
            .build();
    }

}
