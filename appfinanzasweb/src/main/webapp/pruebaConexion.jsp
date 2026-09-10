<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prueba de conexión</title>
</head>

<body>

    <h1>Prueba de conexión con MySQL</h1>

    <p>${estadoConexion}</p>

    <p style="color: red;">
        ${detalleError}
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/">
            Volver al inicio
        </a>
    </p>

</body>
</html>