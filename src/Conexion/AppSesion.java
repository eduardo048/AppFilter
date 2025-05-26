//Clase para manejar las credenciales de sesión usuario y contraseña en la aplicación.
package Conexion;

public class AppSesion {
    private static String usuario;
    private static String contraseña;
    
    public static void setCredenciales(String user, String contra){
        usuario = user;
        contraseña = contra;
    }

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        AppSesion.usuario = usuario;
    }

    public static String getContraseña() {
        return contraseña;
    }

    public static void setContraseña(String contraseña) {
        AppSesion.contraseña = contraseña;
    }    
}
