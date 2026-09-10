package com.finanzas.appfinanzasweb.servlet;

import com.finanzas.appfinanzasweb.dao.MovimientoDAO;
import com.finanzas.appfinanzasweb.modelo.Movimiento;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
        name = "ListaMovimientosServlet",
        urlPatterns = {"/movimientos"}
)
public class ListaMovimientosServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String idUsuarioTexto =
                request.getParameter("idUsuario");

        if (idUsuarioTexto == null
                || idUsuarioTexto.isBlank()) {

            request.setAttribute(
                    "error",
                    "Debes ingresar el identificador del usuario."
            );

            request.getRequestDispatcher("/listaMovimientos.jsp")
                    .forward(request, response);

            return;
        }

        try {

            int idUsuario =
                    Integer.parseInt(idUsuarioTexto);

            if (idUsuario <= 0) {

                request.setAttribute(
                        "error",
                        "El identificador del usuario debe ser mayor que cero."
                );

                request.getRequestDispatcher("/listaMovimientos.jsp")
                        .forward(request, response);

                return;
            }

            MovimientoDAO movimientoDAO =
                    new MovimientoDAO();

            List<Movimiento> movimientos =
                    movimientoDAO.listarMovimientosPorUsuario(
                            idUsuario
                    );

            request.setAttribute(
                    "idUsuario",
                    idUsuario
            );

            request.setAttribute(
                    "movimientos",
                    movimientos
            );

            request.getRequestDispatcher("/listaMovimientos.jsp")
                    .forward(request, response);

        } catch (NumberFormatException error) {

            request.setAttribute(
                    "error",
                    "El identificador del usuario debe ser numérico."
            );

            request.getRequestDispatcher("/listaMovimientos.jsp")
                    .forward(request, response);
        }
    }
}