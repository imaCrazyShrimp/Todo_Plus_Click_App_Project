package relacion.proyecto_todo_plus.conexionBD;

import relacion.proyecto_todo_plus.arbolBinario.producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class persistenciaDAO {

    //metodo para insertar un producto 
    public boolean insertar(producto p) {
        String sql = "INSERT INTO productos VALUES (?,?,?,?,?,?,?)";
        try {
            Connection con = Conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getCodigo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDescripcion());
            ps.setDouble(4, p.getPrecio());
            ps.setString(5, p.getColor());
            ps.setString(6, p.getCategoria());
            ps.setString(7, p.getMarca());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al ingresar: " + e.getMessage());
            return false;
        }
    }

    //metodo para buscar por el codigo 
    public producto buscar(int codigo) {
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        try {
            Connection con = Conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                producto p = new producto(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("color"),
                        rs.getString("categoria"),
                        rs.getString("marca")
                );
                con.close();
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    //Cargar todos los producto para inicial con el arbol
    public producto[] cargarTodos() {
        String sql = "SELECT * FROM productos";
        producto[] lista = new producto[0];
        try {
            Connection con = Conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql,
                    //sirve para recorrer los datos del inicio hata el ultimo
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            //contea cuantos hay 
            rs.last();
            int total = rs.getRow();
            rs.beforeFirst();
            lista = new producto[total];
            int i = 0;
            while (rs.next()) {
                lista[i] = new producto(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("color"),
                        rs.getString("categoria"),
                        rs.getString("marca")
                );
                i++;
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
        return lista;
    }

    //metodo para editar o actualizar
    public boolean editar(producto p) {
        String sql = "UPDATE productos SET nombre=?, descripcion=?, precio=?, color=?, categoria=?, marca =? WHERE codigo=?";
        try {
            Connection con = Conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecio());
            ps.setString(4, p.getColor());
            ps.setString(5, p.getCategoria());
            ps.setString(6, p.getMarca());
            ps.setInt(7, p.getCodigo());
            ps.executeUpdate();
            con.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
        return false;
    }
    
    //metodo para eliminar 
    public boolean eliminar(int codigo){
        String sql = "DELETE FROM productos WHERE codigo=?";
        try{
            Connection con = Conexion.obtenerConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.executeUpdate();
            con.close();
            return true;
        }catch(SQLException e){
            System.out.println("Error al eliminar: " + e.getMessage());
        }
        return false;
    }
    
}
