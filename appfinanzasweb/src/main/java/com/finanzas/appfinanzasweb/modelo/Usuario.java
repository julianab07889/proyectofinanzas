package com.finanzas.appfinanzasweb.modelo;

/**
 * Representa un usuario del sistema.
 * Contiene la información necesaria para el registro
 * y la autenticación.
 */
public class Usuario {

    private int idUsuario;
    private String nombre;
    private String correo;
    private String contrasena;
    private String estado;

    // Constructor vacío.
    public Usuario() {
    }

    // Constructor utilizado para crear nuevos usuarios.
    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.estado = "Activo";
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}