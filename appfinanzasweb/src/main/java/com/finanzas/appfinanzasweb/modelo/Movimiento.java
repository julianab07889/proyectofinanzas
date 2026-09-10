package com.finanzas.appfinanzasweb.modelo;

import java.math.BigDecimal;

public class Movimiento {

    private int idMovimiento;
    private int idUsuario;
    private int idCategoria;
    private String nombreCategoria;
    private String fecha;
    private String tipoMovimiento;
    private String descripcion;
    private BigDecimal monto;

    public Movimiento() {
    }

    public Movimiento(
            int idUsuario,
            int idCategoria,
            String fecha,
            String tipoMovimiento,
            String descripcion,
            BigDecimal monto
    ) {
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public Movimiento(
            int idMovimiento,
            int idUsuario,
            int idCategoria,
            String fecha,
            String tipoMovimiento,
            String descripcion,
            BigDecimal monto
    ) {
        this.idMovimiento = idMovimiento;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}