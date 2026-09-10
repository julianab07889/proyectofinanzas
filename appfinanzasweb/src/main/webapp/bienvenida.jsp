<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Prueba del Servlet GET</title>
</head>

<body>

    <h1>Prueba de Servlet con GET</h1>

    <p>Mensaje recibido desde Java:</p>

    <strong>${mensaje}</strong>

    <p>
        Fecha y hora de la solicitud:
        ${fechaSolicitud}
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/">
            Volver a la página principal
        </a>
    </p>

</body>
</html>