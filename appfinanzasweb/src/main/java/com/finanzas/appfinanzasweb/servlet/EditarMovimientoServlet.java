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
        name = "EditarMovimientoServlet",
        urlPatterns = {"/editar-movimiento"}
)
public class EditarMovimientoServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String idMovimientoTexto =
                request.getParameter("idMovimiento");

        String idUsuarioTexto =
                request.getParameter("idUsuario");

        try {

            int idMovimiento =
                    Integer.parseInt(idMovimientoTexto);

            int idUsuario =
                    Integer.parseInt(idUsuarioTexto);

            MovimientoDAO movimientoDAO =
                    new MovimientoDAO();

            Movimiento movimiento =
                    movimientoDAO.buscarMovimientoPorId(
                            idMovimiento,
                            idUsuario
                    );

            if (movimiento == null) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/movimientos?idUsuario="
                        + idUsuario
                );

                return;
            }

            request.setAttribute(
                    "movimiento",
                    movimiento
            );

            request.getRequestDispatcher(
                    "/editarMovimiento.jsp"
            ).forward(request, response);

        } catch (NumberFormatException error) {

            response.sendRedirect(
                    request.getContextPath() + "/"
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idMovimientoTexto =
                request.getParameter("idMovimiento");

        String idUsuarioTexto =
                request.getParameter("idUsuario");

        String idCategoriaTexto =
                request.getParameter("idCategoria");

        String fecha =
                request.getParameter("fecha");

        String tipo =
                request.getParameter("tipo");

        String descripcion =
                request.getParameter("descripcion");

        String valorTexto =
                request.getParameter("valor");

        try {

            int idMovimiento =
                    Integer.parseInt(idMovimientoTexto);

            int idUsuario =
                    Integer.parseInt(idUsuarioTexto);

            int idCategoria =
                    Integer.parseInt(idCategoriaTexto);

            BigDecimal monto =
                    new BigDecimal(valorTexto);

            Movimiento movimiento =
                    new Movimiento();

            movimiento.setIdMovimiento(idMovimiento);
            movimiento.setIdUsuario(idUsuario);
            movimiento.setIdCategoria(idCategoria);
            movimiento.setFecha(fecha);
            movimiento.setTipoMovimiento(tipo);
            movimiento.setDescripcion(descripcion);
            movimiento.setMonto(monto);

            MovimientoDAO movimientoDAO =
                    new MovimientoDAO();

            boolean actualizado =
                    movimientoDAO.actualizarMovimiento(
                            movimiento
                    );

            if (actualizado) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/movimientos?idUsuario="
                        + idUsuario
                        + "&actualizado=true"
                );

            } else {

                request.setAttribute(
                        "error",
                        "No fue posible actualizar el movimiento."
                );

                request.setAttribute(
                        "movimiento",
                        movimiento
                );

                request.getRequestDispatcher(
                        "/editarMovimiento.jsp"
                ).forward(request, response);
            }

        } catch (NumberFormatException error) {

            request.setAttribute(
                    "error",
                    "Los identificadores y el valor deben ser numéricos."
            );

            request.getRequestDispatcher(
                    "/editarMovimiento.jsp"
            ).forward(request, response);
        }
    }
}