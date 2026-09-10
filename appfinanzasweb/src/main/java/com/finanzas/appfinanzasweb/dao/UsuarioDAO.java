package com.finanzas.appfinanzasweb.dao;

import com.finanzas.appfinanzasweb.conexion.ConexionDB;
import com.finanzas.appfinanzasweb.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase encargada de realizar las operaciones de
 * registro y autenticación de usuarios en la base de datos.
 */
public class UsuarioDAO {

    /**
     * Registra un nuevo usuario en la tabla usuarios.
     *
     * @param usuario usuario que se desea registrar
     * @return true si el registro fue exitoso
     */
    public boolean registrarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios "
                + "(nombre, correo, contrasena, estado) "
                + "VALUES (?, ?, ?, 'Activo')";

        try {

            // Se establece la conexión con la base de datos.
            Connection conexion = ConexionDB.obtenerConexion();

            // Se prepara la sentencia SQL.
            PreparedStatement consulta = conexion.prepareStatement(sql);

            // Se asignan los valores recibidos.
            consulta.setString(1, usuario.getNombre());
            consulta.setString(2, usuario.getCorreo());
            consulta.setString(3, usuario.getContrasena());

            // Ejecuta el registro y devuelve las filas modificadas.
            int filasInsertadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            // Si se insertó una fila, el registro fue exitoso.
            return filasInsertadas > 0;

        } catch (SQLException error) {

            System.out.println("Error al registrar usuario.");
            System.out.println("Detalle: " + error.getMessage());

            return false;
        }
    }

    /**
     * Valida el correo y la contraseña ingresados.
     *
     * @param correo correo del usuario
     * @param contrasena contraseña ingresada
     * @return true si las credenciales son correctas
     */
    public boolean validarLogin(String correo, String contrasena) {

        String sql = "SELECT id_usuario "
                + "FROM usuarios "
                + "WHERE correo = ? "
                + "AND contrasena = ? "
                + "AND estado = 'Activo'";

        try {

            // Se establece conexión con la base de datos.
          Connection conexion = ConexionDB.obtenerConexion();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            // Se asignan las credenciales recibidas.
            consulta.setString(1, correo);
            consulta.setString(2, contrasena);

            ResultSet resultado = consulta.executeQuery();

            // Si existe un registro, las credenciales son correctas.
            boolean autenticado = resultado.next();

            resultado.close();
            consulta.close();
            conexion.close();

            return autenticado;

        } catch (SQLException error) {

            System.out.println("Error al validar el inicio de sesión.");
            System.out.println("Detalle: " + error.getMessage());

            return false;
        }
    }
}