package com.gk.portfolio.gateways;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


public interface CurrencyGateway {
    String URL = "http://data.fixer.io/api";

    @GET
    @Path("/latest")
    @Produces(MediaType.APPLICATION_JSON)
    String getRates(@QueryParam("symbols") String symbols,
                    @QueryParam("base") String base,
                    @QueryParam("access_key") String accessKey);
}
