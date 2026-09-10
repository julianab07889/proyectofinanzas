<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registrar movimiento</title>
</head>

<body>

    <h1>Registrar movimiento financiero</h1>

    <p>
        Los datos enviados mediante POST serán guardados
        en la base de datos MySQL.
    </p>

    <%
        String error = (String) request.getAttribute("error");

        if (error != null) {
    %>

        <p style="color: red;">
            <strong><%= error %></strong>
        </p>

    <%
        }
    %>

    <form
        action="${pageContext.request.contextPath}/movimiento-prueba"
        method="post"
    >

        <p>
            <label for="idUsuario">
                Identificador del usuario:
            </label>

            <input
                type="number"
                id="idUsuario"
                name="idUsuario"
                min="1"
                required
            >
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
                required
            >
        </p>

        <p>
            <label for="tipo">
                Tipo de movimiento:
            </label>

            <select id="tipo" name="tipo" required>
                <option value="">Seleccione</option>
                <option value="Ingreso">Ingreso</option>
                <option value="Gasto">Gasto</option>
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
                required
            >
        </p>

        <p>
            <label for="valor">
                Valor:
            </label>

            <input
                type="number"
                id="valor"
                name="valor"
                min="1"
                step="0.01"
                required
            >
        </p>

        <p>
            <label for="fecha">
                Fecha:
            </label>

            <input
                type="date"
                id="fecha"
                name="fecha"
                required
            >
        </p>

        <button type="submit">
            Guardar movimiento
        </button>

    </form>

    <p>
        <a href="${pageContext.request.contextPath}/">
            Volver al inicio
        </a>
    </p>

</body>
</html>