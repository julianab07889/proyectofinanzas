<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.finanzas.appfinanzasweb.modelo.Movimiento"%>

<%
    Movimiento movimiento =
            (Movimiento) request.getAttribute("movimiento");

    String error =
            (String) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <title>Editar movimiento</title>
</head>

<body>

    <h1>Editar movimiento financiero</h1>

    <% if (error != null) { %>

        <p style="color: red;">
            <strong><%= error %></strong>
        </p>

    <% } %>

    <% if (movimiento != null) { %>

        <form
            action="${pageContext.request.contextPath}/editar-movimiento"
            method="post"
        >

            <input
                type="hidden"
                name="idMovimiento"
                value="<%= movimiento.getIdMovimiento() %>"
            >

            <input
                type="hidden"
                name="idUsuario"
                value="<%= movimiento.getIdUsuario() %>"
            >

            <p>
                <strong>ID del movimiento:</strong>
                <%= movimiento.getIdMovimiento() %>
            </p>

            <p>
                <label for="idCategoria">
                    Identificador de la categoría:
                </label>

                <input
                    type="number"
                    id="idCategoria"
                    name="idCategoria"
                    min="1"
                    value="<%= movimiento.getIdCategoria() %>"
                    required
                >
            </p>

            <p>
                <label for="fecha">Fecha:</label>

                <input
                    type="date"
                    id="fecha"
                    name="fecha"
                    value="<%= movimiento.getFecha() %>"
                    required
                >
            </p>

            <p>
                <label for="tipo">
                    Tipo de movimiento:
                </label>

                <select id="tipo" name="tipo" required>

                    <option
                        value="Ingreso"
                        <%= "Ingreso".equals(
                                movimiento.getTipoMovimiento()
                            ) ? "selected" : "" %>
                    >
                        Ingreso
                    </option>

                    <option
                        value="Gasto"
                        <%= "Gasto".equals(
                                movimiento.getTipoMovimiento()
                            ) ? "selected" : "" %>
                    >
                        Gasto
                    </option>

                </select>
            </p>

            <p>
                <label for="descripcion">
                    Descripción:
                </label>

                <input
                    type="text"
                    id="descripcion"
                    name="descripcion"
                    maxlength="150"
                    value="<%= movimiento.getDescripcion() %>"
                    required
                >
            </p>

            <p>
                <label for="valor">Valor:</label>

                <input
                    type="number"
                    id="valor"
                    name="valor"
                    min="1"
                    step="0.01"
                    value="<%= movimiento.getMonto() %>"
                    required
                >
            </p>

            <button type="submit">
                Guardar cambios
            </button>

        </form>

        <p>
            <a href="${pageContext.request.contextPath}/movimientos?idUsuario=<%= movimiento.getIdUsuario() %>">
                Cancelar y volver al listado
            </a>
        </p>

    <% } else { %>

        <p>No fue posible cargar el movimiento.</p>

    <% } %>

</body>
</html>