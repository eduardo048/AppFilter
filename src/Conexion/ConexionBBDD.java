/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author PC
 */
public class ConexionBBDD {
    private Connection conexion;
    private Statement sentencia;
    private PreparedStatement sentenciaPreparada;
    private ResultSet resultado;
    private boolean conectado;
    
    public ConexionBBDD(){
        String url = "jdbc:oracle:thin:@//10.147.20.17:1521/XEPDB1";
        String usuario = AppSesion.getUsuario();
        String contraseña = AppSesion.getContraseña();
        try{
            Class.forName("oracle.jdbc.OracleDriver");
            this.conexion = DriverManager.getConnection(url, usuario, contraseña);
           
            conectado = true;
        }catch (ClassNotFoundException e){
            conectado = false;
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public boolean estaConectado(){
        return conectado;
    }
    
    
    
    
}
