## Módulo Java con JDBC

El proyecto incluye un módulo desarrollado en Java utilizando JDBC para la conexión con la base de datos MySQL. Este módulo se encuentra en la carpeta `backend-java-jdbc`.

### Estructura del módulo Java

- `conexion`: contiene la clase `ConexionDB`, encargada de realizar la conexión con MySQL.
- `modelo`: contiene las clases que representan las entidades del sistema, como Usuario, Movimiento, Categoria, MetaFinanciera y Presupuesto.
- `dao`: contiene las clases responsables de realizar operaciones sobre la base de datos.
- `appfinanzasjava`: contiene la clase principal para ejecutar las pruebas del sistema.

### Funcionalidades implementadas

- Conexión a la base de datos MySQL mediante JDBC.
- Validación de usuario mediante correo y contraseña.
- Consulta de categorías.
- CRUD completo de movimientos financieros.
- CRUD completo de metas financieras.
- CRUD completo de presupuestos.

### Tecnologías utilizadas

- Java
- JDBC
- MySQL
- NetBeans
- GitHub
