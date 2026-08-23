# Sistema de Finanzas Personales

Proyecto formativo desarrollado como una aplicación web para apoyar el registro y manejo de movimientos financieros personales.

## Módulo web con Servlets y JSP

El proyecto incluye un módulo web desarrollado en Java utilizando Servlets, JSP, JDBC y MySQL.

El módulo permite recibir información desde formularios web, procesarla mediante Servlets y almacenar los datos en una base de datos MySQL.

## Funcionamiento del módulo

El flujo principal de la aplicación es:

**Usuario → Formulario JSP/HTML → Servlet → Java/JDBC → MySQL → JSP**

El usuario puede ingresar información de un movimiento financiero mediante un formulario. Los datos son enviados al Servlet, procesados en Java y almacenados en la base de datos.

Posteriormente, el sistema muestra el resultado mediante una página JSP.

## Métodos HTTP utilizados

### GET

El método GET se utiliza para solicitar y mostrar información.

En el módulo de movimientos se utiliza para acceder al formulario desde el Servlet.

### POST

El método POST se utiliza para enviar la información ingresada por el usuario.

En el módulo de movimientos permite enviar los datos del formulario al Servlet para posteriormente almacenarlos en MySQL.

## Estructura principal

El proyecto está organizado en diferentes componentes:

* **conexion:** contiene la clase encargada de realizar la conexión con MySQL.
* **modelo:** contiene las clases que representan los datos del sistema, entre ellas `Movimiento`.
* **dao:** contiene las clases encargadas de realizar las operaciones sobre la base de datos mediante JDBC.
* **servlet:** contiene los Servlets encargados de recibir y procesar las solicitudes HTTP.
* **webapp:** contiene las páginas JSP y los formularios utilizados por el usuario.

## Funcionalidades implementadas

* Conexión con una base de datos MySQL mediante JDBC.
* Uso de formularios HTML dentro de páginas JSP.
* Procesamiento de solicitudes mediante Servlets.
* Uso del método GET.
* Uso del método POST.
* Registro de movimientos financieros.
* Almacenamiento de movimientos en MySQL.
* Presentación del resultado mediante JSP.
* Prueba de conexión entre la aplicación Java y la base de datos.

## Tecnologías utilizadas

* Java
* Jakarta Servlets
* JSP
* HTML
* JDBC
* MySQL
* Maven
* Apache Tomcat
* NetBeans
* Git
* GitHub

## Evidencia

**GA7-220501096-AA2-EV02 – Módulos de software codificados y probados**

El módulo desarrollado permite demostrar el uso de formularios web, Servlets, métodos GET y POST, páginas JSP y conexión con una base de datos MySQL.
