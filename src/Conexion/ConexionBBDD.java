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
import javax.swing.JLabel;

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
    
    public void cerrarConexion(){
        try{
           if( resultado != null ) resultado.close(); 
           if( sentenciaPreparada !=null ) sentenciaPreparada.close();
           if( sentencia !=null ) sentencia.close();
           if( conexion != null ) conexion.close();
        }catch(SQLException e){
            e.printStackTrace();
        }       
    }
    
    public boolean empresaExiste(String id){
        String sql = "SELECT COUNT(*) FROM EMPRESAS WHERE ID = ?";
        try{
            sentenciaPreparada = conexion.prepareStatement(sql);
            sentenciaPreparada.setString(1, sql);
            resultado = sentenciaPreparada.executeQuery();
            if(resultado.next()){
                return resultado.getInt(1) > 0;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean insertarEmpresa(String id, String empresa, String actividad, String sector,
                                   String direccion, String cp, String poblacion, String provincia,
                                   String comunidad, String telefono, String fax,
                                   String email, String emailTest, String web, JLabel estado){
    
        String sql = "INSERT INTO EMPRESAS (ID, EMPRESA, ACTIVIDAD, SECTOR, DIRECCION, CP, POBLACION, PROVINCIA, COMUNIDAD, TELEFONO, FAX, EMAIL, EMAIL_TEST, WEB)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try{
            sentenciaPreparada = conexion.prepareStatement(sql);
            sentenciaPreparada.setString(1, id);
            sentenciaPreparada.setString(2, empresa);
            sentenciaPreparada.setString(3, actividad);
            sentenciaPreparada.setString(4, sector);
            sentenciaPreparada.setString(5, direccion);
            sentenciaPreparada.setString(6, cp);
            sentenciaPreparada.setString(7, poblacion);
            sentenciaPreparada.setString(8, provincia);
            sentenciaPreparada.setString(9, comunidad);
            sentenciaPreparada.setString(10, telefono);
            sentenciaPreparada.setString(11, fax);
            sentenciaPreparada.setString(12, email);
            sentenciaPreparada.setString(13, emailTest);
            sentenciaPreparada.setString(14, web);
            
            int filas = sentenciaPreparada.executeUpdate();
            if(filas > 0){
                return true;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    
    }
    
    
    
    
}
