<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Movimiento guardado</title>
</head>

<body>

    <h1>Movimiento guardado correctamente</h1>

    <p>
        <strong>${mensaje}</strong>
    </p>

    <table border="1" cellpadding="8">

        <tr>
            <th>Campo</th>
            <th>Información registrada</th>
        </tr>

        <tr>
            <td>ID generado</td>
            <td>${idGenerado}</td>
        </tr>

        <tr>
            <td>ID usuario</td>
            <td>${idUsuario}</td>
        </tr>

        <tr>
            <td>ID categoría</td>
            <td>${idCategoria}</td>
        </tr>

        <tr>
            <td>Tipo</td>
            <td>${tipo}</td>
        </tr>

        <tr>
            <td>Descripción</td>
            <td>${descripcion}</td>
        </tr>

        <tr>
            <td>Valor</td>
            <td>$ ${valor}</td>
        </tr>

        <tr>
            <td>Fecha</td>
            <td>${fecha}</td>
        </tr>

    </table>

    <p>
        <a href="${pageContext.request.contextPath}/movimiento-prueba">
            Registrar otro movimiento
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/">
            Volver al inicio
        </a>
    </p>

</body>
</html>