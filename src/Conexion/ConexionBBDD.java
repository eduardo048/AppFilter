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
import java.util.ArrayList;
import java.util.HashMap;
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
    
    public ArrayList<String> buscarEmpresaPorNombreParcial(String nombrePar){
        ArrayList<String> nombreEmpresas = new ArrayList<>();
        
        if(conexion == null){
            return nombreEmpresas;
        }
        
        String sql = "SELECT EMPRESA FROM EMPRESAS WHERE UPPER(EMPRESA) LIKE UPPER(?)";
        try{
            sentenciaPreparada = conexion.prepareStatement(sql);
            sentenciaPreparada.setString(1, "%" + nombrePar + "%");
            resultado = sentenciaPreparada.executeQuery();
            
            while(resultado.next()){
                nombreEmpresas.add(resultado.getString("EMPRESA"));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return nombreEmpresas;
    }
    
    public HashMap<String, String> buscarEmpresaPorNombreEntero(String nombre){
        HashMap<String, String> datos = new HashMap<>();
        
        if(conexion == null){
            return null;
        }
        
        String sql = "SELECT * FROM EMPRESAS WHERE UPPER(EMPRESA) = UPPER(?)";
        try{
            sentenciaPreparada = conexion.prepareStatement(sql);
            sentenciaPreparada.setString(1, nombre);
            resultado = sentenciaPreparada.executeQuery();
            
            if(resultado.next()){
                datos.put("id", resultado.getString("ID"));
                datos.put("nombre", resultado.getString("EMPRESA"));
                datos.put("actividad", resultado.getString("ACTIVIDAD"));
                datos.put("sector", resultado.getString("SECTOR"));
                datos.put("direccion", resultado.getString("DIRECCION"));
                datos.put("cp", resultado.getString("CP"));
                datos.put("poblacion", resultado.getString("POBLACION"));
                datos.put("provincia", resultado.getString("PROVINCIA"));
                datos.put("comunidad", resultado.getString("COMUNIDAD"));
                datos.put("telefono", resultado.getString("TELEFONO"));
                datos.put("fax", resultado.getString("FAX"));
                datos.put("email", resultado.getString("EMAIL"));
                datos.put("emailTest", resultado.getString("EMAIL_TEST"));
                datos.put("web", resultado.getString("WEB"));
                return datos;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public boolean eliminarEmpresaPorId(String id){
        if(conexion == null){
            return false;
        }
        
        String sql = "DELETE FROM EMPRESAS WHERE ID = ?";
        
        try{
            sentenciaPreparada = conexion.prepareStatement(sql);
            sentenciaPreparada.setString(1, id);
            int filas = sentenciaPreparada.executeUpdate();
            return filas > 0;
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean modificarEmpresaPorId(String id, HashMap<String, String> nuevosDatos) {
        if (conexion == null || id == null || id.isEmpty()) {
            return false;
        }

        StringBuilder sql = new StringBuilder("UPDATE EMPRESAS SET ");
        ArrayList<String> campos = new ArrayList<>();
        ArrayList<String> valores = new ArrayList<>();

        for (String clave : nuevosDatos.keySet()) {
            String valor = nuevosDatos.get(clave);
            if (valor != null && !valor.isEmpty()) {
                campos.add(clave.toUpperCase() + " = ?");
                valores.add(valor);
            }
        }

        if (campos.isEmpty()) {
            return false;
        }

        sql.append(String.join(", ", campos));
        sql.append(" WHERE ID = ?");

        try {
            sentenciaPreparada = conexion.prepareStatement(sql.toString());

            int i = 1;
            for (String valor : valores) {
                sentenciaPreparada.setString(i++, valor);
            }
            sentenciaPreparada.setString(i, id);

            int filas = sentenciaPreparada.executeUpdate();
            return filas > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
    
    
}
