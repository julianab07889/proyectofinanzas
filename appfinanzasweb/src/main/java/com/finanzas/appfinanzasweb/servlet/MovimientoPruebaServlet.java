package com.finanzas.appfinanzasweb.servlet;

import com.finanzas.appfinanzasweb.dao.MovimientoDAO;
import com.finanzas.appfinanzasweb.modelo.Movimiento;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
        name = "MovimientoPruebaServlet",
        urlPatterns = {"/movimiento-prueba"}
)
public class MovimientoPruebaServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.getRequestDispatcher("/formularioMovimiento.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idUsuarioTexto =
                request.getParameter("idUsuario");

        String idCategoriaTexto =
                request.getParameter("idCategoria");

        String tipo =
                request.getParameter("tipo");

        String descripcion =
                request.getParameter("descripcion");

        String valorTexto =
                request.getParameter("valor");

        String fecha =
                request.getParameter("fecha");

        if (idUsuarioTexto == null || idUsuarioTexto.isBlank()
                || idCategoriaTexto == null || idCategoriaTexto.isBlank()
                || tipo == null || tipo.isBlank()
                || descripcion == null || descripcion.isBlank()
                || valorTexto == null || valorTexto.isBlank()
                || fecha == null || fecha.isBlank()) {

            request.setAttribute(
                    "error",
                    "Todos los campos son obligatorios."
            );

            request.getRequestDispatcher("/formularioMovimiento.jsp")
                    .forward(request, response);

            return;
        }

        try {

            int idUsuario =
                    Integer.parseInt(idUsuarioTexto);

            int idCategoria =
                    Integer.parseInt(idCategoriaTexto);

            BigDecimal monto =
                    new BigDecimal(valorTexto);

            if (idUsuario <= 0
                    || idCategoria <= 0
                    || monto.compareTo(BigDecimal.ZERO) <= 0) {

                request.setAttribute(
                        "error",
                        "El usuario, la categoría y el valor deben ser mayores que cero."
                );

                request.getRequestDispatcher("/formularioMovimiento.jsp")
                        .forward(request, response);

                return;
            }

            Movimiento movimiento = new Movimiento();

            movimiento.setIdUsuario(idUsuario);
            movimiento.setIdCategoria(idCategoria);
            movimiento.setFecha(fecha);
            movimiento.setTipoMovimiento(tipo);
            movimiento.setDescripcion(descripcion);
            movimiento.setMonto(monto);

            MovimientoDAO movimientoDAO =
                    new MovimientoDAO();

            int idGenerado =
                    movimientoDAO.insertarMovimiento(movimiento);

            if (idGenerado > 0) {

                request.setAttribute(
                        "mensaje",
                        "El movimiento fue guardado correctamente en MySQL."
                );

                request.setAttribute(
                        "idGenerado",
                        idGenerado
                );

                request.setAttribute(
                        "idUsuario",
                        idUsuario
                );

                request.setAttribute(
                        "idCategoria",
                        idCategoria
                );

                request.setAttribute("tipo", tipo);
                request.setAttribute("descripcion", descripcion);
                request.setAttribute("valor", monto);
                request.setAttribute("fecha", fecha);

                request.getRequestDispatcher("/resultadoMovimiento.jsp")
                        .forward(request, response);

            } else {

                request.setAttribute(
                        "error",
                        "No fue posible guardar el movimiento. Revisa que el usuario y la categoría existan."
                );

                request.getRequestDispatcher("/formularioMovimiento.jsp")
                        .forward(request, response);
            }

        } catch (NumberFormatException error) {

            request.setAttribute(
                    "error",
                    "El usuario, la categoría y el valor deben ser numéricos."
            );

            request.getRequestDispatcher("/formularioMovimiento.jsp")
                    .forward(request, response);
        }
    }
}