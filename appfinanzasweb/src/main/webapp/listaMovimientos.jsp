<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.List"%>
<%@page import="com.finanzas.appfinanzasweb.modelo.Movimiento"%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Movimientos financieros</title>
</head>

<body>

    <h1>Movimientos financieros</h1>

    <%
        String error =
                (String) request.getAttribute("error");

        List<Movimiento> movimientos =
                (List<Movimiento>)
                request.getAttribute("movimientos");

        Integer idUsuario =
                (Integer) request.getAttribute("idUsuario");
    %>

    <% if (error != null) { %>

        <p style="color: red;">
            <strong><%= error %></strong>
        </p>

    <% } else { %>

        <p>
            Movimientos registrados para el usuario:
            <strong><%= idUsuario %></strong>
        </p>

        <% if (movimientos == null
                || movimientos.isEmpty()) { %>

            <p>
                El usuario todavía no tiene movimientos registrados.
            </p>

        <% } else { %>

            <table border="1" cellpadding="8">

                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Tipo</th>
                    <th>Categoría</th>
                    <th>Descripción</th>
                    <th>Monto</th>
                    <th>Acciones</th>
                </tr>

                <% for (Movimiento movimiento : movimientos) { %>

                    <tr>
                        <td>
                            <%= movimiento.getIdMovimiento() %>
                        </td>

                        <td>
                            <%= movimiento.getFecha() %>
                        </td>

                        <td>
                            <%= movimiento.getTipoMovimiento() %>
                        </td>

                        <td>
                            <%= movimiento.getNombreCategoria() %>
                        </td>

                        <td>
                            <%= movimiento.getDescripcion() %>
                        </td>

                        <td>
                            $ <%= movimiento.getMonto() %>
                        </td>
                        <td>
    <a href="${pageContext.request.contextPath}/editar-movimiento?idMovimiento=<%= movimiento.getIdMovimiento() %>&idUsuario=<%= movimiento.getIdUsuario() %>">
        Editar
    </a>
</td>
                    </tr>

                <% } %>

            </table>

        <% } %>

    <% } %>

    <p>
        <a href="${pageContext.request.contextPath}/movimiento-prueba">
            Registrar un nuevo movimiento
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/">
            Volver al inicio
        </a>
    </p>

</body>
</html>