package com.finanzas.appfinanzasweb.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión
 * con la base de datos MySQL.
 */
public class ConexionDB {

    // Dirección de la base de datos.
    private static final String URL
            = "jdbc:mysql://localhost:3306/app_finanzas"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC";

    // Usuario de MySQL.
    private static final String USUARIO = "finanzas_user";

    // Contraseña del usuario de MySQL.
    private static final String CONTRASENA = "1234";

    /**
     * Constructor privado para evitar crear objetos
     * directamente de esta clase.
     */
    private ConexionDB() {
        // Evita crear objetos de esta clase.
    }

    /**
     * Carga el driver de MySQL cuando se inicia la clase.
     */
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException error) {
            System.out.println("No se encontró el driver de MySQL.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }

    /**
     * Obtiene una conexión con la base de datos.
     *
     * @return conexión activa con MySQL
     * @throws SQLException si ocurre un error de conexión
     */
    public static Connection obtenerConexion() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }
}