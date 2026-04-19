package relacion.proyecto_todo_plus.conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    //la base de datos tiene como nombre "producto"
    private static final String URL = "jdbc:mysql://localhost:3306/producto";
    private static final String USUARIO = "root";
    private static final String PASWORD = "";

    public static Connection obtenerConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
        return conexion;
    }

}
