package com.finanzas.appfinanzasweb.resources;

import com.finanzas.appfinanzasweb.dao.UsuarioDAO;
import com.finanzas.appfinanzasweb.modelo.Usuario;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Servicio web encargado del registro
 * y autenticación de usuarios.
 */
@Path("usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Servicio para registrar un nuevo usuario.
     *
     * Ruta:
     * POST /resources/usuarios/registro
     */
    @POST
    @Path("registro")
    public Response registrarUsuario(Usuario usuario) {

        // Verifica que se hayan recibido los datos necesarios.
        if (usuario == null
                || usuario.getNombre() == null
                || usuario.getCorreo() == null
                || usuario.getContrasena() == null) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"mensaje\":\"Datos de registro incompletos\"}")
                    .build();
        }

        // Intenta registrar el usuario en la base de datos.
        boolean registrado = usuarioDAO.registrarUsuario(usuario);

        if (registrado) {

            return Response
                    .status(Response.Status.CREATED)
                    .entity("{\"mensaje\":\"Usuario registrado correctamente\"}")
                    .build();
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"mensaje\":\"Error al registrar el usuario\"}")
                .build();
    }


    /**
     * Servicio para validar el inicio de sesión.
     *
     * Ruta:
     * POST /resources/usuarios/login
     */
    @POST
    @Path("login")
    public Response iniciarSesion(Usuario usuario) {

        // Verifica que correo y contraseña hayan sido enviados.
        if (usuario == null
                || usuario.getCorreo() == null
                || usuario.getContrasena() == null) {

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("{\"mensaje\":\"Correo y contraseña son obligatorios\"}")
                    .build();
        }

        // Consulta en la base de datos si las credenciales son correctas.
        boolean autenticado = usuarioDAO.validarLogin(
                usuario.getCorreo(),
                usuario.getContrasena()
        );

        if (autenticado) {

            return Response
                    .ok("{\"mensaje\":\"Autenticación satisfactoria\"}")
                    .build();
        }

        return Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("{\"mensaje\":\"Error en la autenticación\"}")
                .build();
    }
}