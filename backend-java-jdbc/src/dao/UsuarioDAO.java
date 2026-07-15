package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public void listarUsuarios() {

        String sql = "SELECT id_usuario, nombre, correo, estado FROM usuarios";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {
                int idUsuario = resultado.getInt("id_usuario");
                String nombre = resultado.getString("nombre");
                String correo = resultado.getString("correo");
                String estado = resultado.getString("estado");

                System.out.println("-----------------------------");
                System.out.println("ID: " + idUsuario);
                System.out.println("Nombre: " + nombre);
                System.out.println("Correo: " + correo);
                System.out.println("Estado: " + estado);
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al consultar usuarios.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }

    public boolean validarLogin(String correo, String contrasena) {

        String sql = "SELECT id_usuario, nombre, correo FROM usuarios WHERE correo = ? AND contrasena = ? AND estado = 'Activo'";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, correo);
            consulta.setString(2, contrasena);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                String nombre = resultado.getString("nombre");

                System.out.println("Login correcto. Bienvenido " + nombre + ".");

                resultado.close();
                consulta.close();
                conexion.close();

                return true;
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al validar login.");
            System.out.println("Detalle: " + error.getMessage());
        }

        return false;
    }
    public modelo.Usuario obtenerUsuarioPorLogin(String correo, String contrasena) {

    String sql = "SELECT id_usuario, nombre, correo, estado FROM usuarios WHERE correo = ? AND contrasena = ? AND estado = 'Activo'";

    try {
        Connection conexion = ConexionDB.conectar();

        PreparedStatement consulta = conexion.prepareStatement(sql);

        consulta.setString(1, correo);
        consulta.setString(2, contrasena);

        ResultSet resultado = consulta.executeQuery();

        if (resultado.next()) {

            modelo.Usuario usuario = new modelo.Usuario();

            usuario.setIdUsuario(resultado.getInt("id_usuario"));
            usuario.setNombre(resultado.getString("nombre"));
            usuario.setCorreo(resultado.getString("correo"));
            usuario.setEstado(resultado.getString("estado"));

            resultado.close();
            consulta.close();
            conexion.close();

            return usuario;
        }

        resultado.close();
        consulta.close();
        conexion.close();

    } catch (SQLException error) {
        System.out.println("Error al obtener usuario por login.");
        System.out.println("Detalle: " + error.getMessage());
    }

    return null;
}
}