/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pediapp;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author USER
 */
@Path("usuarios")
public class usuarios {
    @GET
    @Path("/getData")
    @Produces(MediaType.APPLICATION_JSON)
    
    public ArrayList<usuariosModel> getDataInJSON() throws ClassNotFoundException, SQLException
    {
        ArrayList<usuariosModel> tmm=new ArrayList<>();
        Connection con=null;
        String username="postgres";
        String password="admin1234";
        String query="select * from usuarios";
        Class.forName("org.postgresql.Driver");
        con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/PediAppBD",username,password);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next())
        {
            usuariosModel tm=new usuariosModel();
            tm.setId(rs.getInt("id"));
            tm.setNombre(rs.getString("nombre"));
            tm.setEmail(rs.getString("email"));  
            tmm.add(tm);
        }
 
        return tmm;
    }
    
    
    
}
