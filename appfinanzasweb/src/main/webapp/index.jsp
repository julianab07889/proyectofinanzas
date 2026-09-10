<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Finanzas Personales</title>
</head>

<body>

    <h1>Sistema de Finanzas Personales</h1>

    <p>
        Aplicación web desarrollada con Java, JSP,
        Servlets, JDBC y MySQL.
    </p>

    <h2>Información dinámica</h2>

    <p>
        Fecha y hora del servidor:
        <%= new java.util.Date() %>
    </p>
<hr>

<h2>Prueba del primer Servlet</h2>

<p>
    <a href="${pageContext.request.contextPath}/bienvenida">
        Probar solicitud GET
    </a>
</p>

<hr>

<h2>Prueba del método POST</h2>

<p>
    <a href="${pageContext.request.contextPath}/movimiento-prueba">
        Abrir formulario de movimiento
    </a>
</p>
<hr>

<h2>Prueba de conexión con MySQL</h2>

<p>
    <a href="${pageContext.request.contextPath}/prueba-conexion">
        Verificar conexión con la base de datos
    </a>
</p>
<hr>

<h2>Consultar movimientos registrados</h2>

<form
    action="${pageContext.request.contextPath}/movimientos"
    method="get"
>

    <label for="usuarioConsulta">
        Identificador del usuario:
    </label>

    <input
        type="number"
        id="usuarioConsulta"
        name="idUsuario"
        min="1"
        value="1"
        required
    >

    <button type="submit">
        Consultar movimientos
    </button>

</form>
</body>
    </html>