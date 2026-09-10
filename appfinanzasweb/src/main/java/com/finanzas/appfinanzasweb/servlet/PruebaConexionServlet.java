package com.finanzas.appfinanzasweb.servlet;

import com.finanzas.appfinanzasweb.conexion.ConexionDB;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
        name = "PruebaConexionServlet",
        urlPatterns = {"/prueba-conexion"}
)
public class PruebaConexionServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        try (Connection conexion = ConexionDB.obtenerConexion()) {

            if (conexion != null && !conexion.isClosed()) {
                request.setAttribute(
                        "estadoConexion",
                        "Conexión exitosa con la base de datos MySQL."
                );
            }

        } catch (SQLException error) {

            request.setAttribute(
                    "estadoConexion",
                    "No fue posible conectar con MySQL."
            );

            request.setAttribute(
                    "detalleError",
                    error.getMessage()
            );
        }

        request.getRequestDispatcher("/pruebaConexion.jsp")
                .forward(request, response);
    }
}