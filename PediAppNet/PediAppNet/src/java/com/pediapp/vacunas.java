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
@Path ("vacunas")
public class vacunas {
   @GET
    @Path("/getData")
    @Produces(MediaType.APPLICATION_JSON)
    
      public ArrayList<vacunasModel> getDataInJSON() throws ClassNotFoundException, SQLException
    {
        ArrayList<vacunasModel> tmm=new ArrayList<>();
        Connection con=null;
        String username="postgres";
        String password="admin1234";
        String query="select * from vacunas";
        Class.forName("org.postgresql.Driver");
        con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/PediAppBD",username,password);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        while(rs.next())
        {
            vacunasModel tm=new vacunasModel();
            tm.setId_vacuna(rs.getInt("id_vacuna"));
            tm.setNombre(rs.getString("nombre"));
            tm.setDosis(rs.getString("dosis"));
            tm.setEdad(rs.getInt("edad"));
            tm.setFecha(rs.getString("fecha"));
            tm.setLote(rs.getString("lote")); 
            tm.setNombre_medico(rs.getString("nombre_medico"));
            tm.setDescripcion(rs.getString("descripcion"));
            tm.setId_hijo(rs.getInt("id_hijo"));
            tm.setAplicada(rs.getString("aplicada"));
            tm.setMes_aplicacion(rs.getInt("mes_aplicacion"));
            tm.setId_usuario(rs.getInt("id_usuario"));
            tmm.add(tm);
            
        }
 
        return tmm;
    
    }   
}
