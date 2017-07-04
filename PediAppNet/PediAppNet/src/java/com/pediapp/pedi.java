/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pediapp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author USER
 */
@Path("pedi")
public class pedi {
    
    /**
     *
     * @return
     */
    @GET
    @Path ("/getdata")
    @Produces(MediaType.APPLICATION_JSON)
    public String getDataInJSON ()
    {
        return "Pedi";
    }
}
//url:jdbc:postgresql://localhost:5432/PediAppBD
    //driver class org.postgresql.Driver
// [postgres on public]