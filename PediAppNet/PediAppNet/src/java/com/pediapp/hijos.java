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

@Path("hijos")
public class hijos {
    @GET
    @Path("/getData")
    @Produces(MediaType.APPLICATION_JSON)
    
      public ArrayList<hijosModel> getDataInJSON() throws ClassNotFoundException, SQLException
    {
        ArrayList<hijosModel> tmm=new ArrayList<>();
        Connection con=null;
        String username="postgres";
        String password="admin1234";
        String query="select * from hijos";
        Class.forName("org.postgresql.Driver");
        con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/PediAppBD",username,password);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next())
        {
            hijosModel tm=new hijosModel();
            tm.setCedula(rs.getInt("cedula"));
            tm.setNombres(rs.getString("nombres"));
            tm.setApellidos(rs.getString("apellidos"));
            tm.setLugar_nacimiento(rs.getString("lugar_nacimiento"));
            tm.setFecha_nacimiento(rs.getString("fecha_nacimiento"));
            tm.setSexo(rs.getString("sexo")); 
            tm.setNacionalidad(rs.getString("nacionalidad"));
            tm.setId_usuario(rs.getString("id_usuario"));
            tmm.add(tm);
            
        }
 
        return tmm;
    }
    
}
