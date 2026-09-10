package com.finanzas.appfinanzasweb.dao;

import com.finanzas.appfinanzasweb.conexion.ConexionDB;
import com.finanzas.appfinanzasweb.modelo.Movimiento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {

    public int insertarMovimiento(Movimiento movimiento) {

        String sql = "INSERT INTO movimientos "
                + "(id_usuario, id_categoria, fecha, tipo_movimiento, "
                + "descripcion, monto) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conexion = ConexionDB.obtenerConexion();
                PreparedStatement consulta = conexion.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {

            consulta.setInt(1, movimiento.getIdUsuario());
            consulta.setInt(2, movimiento.getIdCategoria());
            consulta.setDate(
                    3,
                    Date.valueOf(movimiento.getFecha())
            );
            consulta.setString(
                    4,
                    movimiento.getTipoMovimiento()
            );
            consulta.setString(
                    5,
                    movimiento.getDescripcion()
            );
            consulta.setBigDecimal(
                    6,
                    movimiento.getMonto()
            );

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {

                try (
                        ResultSet clavesGeneradas =
                                consulta.getGeneratedKeys()
                ) {

                    if (clavesGeneradas.next()) {
                        return clavesGeneradas.getInt(1);
                    }
                }
            }

        } catch (SQLException error) {

            System.out.println(
                    "Error al insertar movimiento."
            );

            System.out.println(
                    "Detalle: " + error.getMessage()
            );
        }

        return 0;
    }

    public List<Movimiento> listarMovimientosPorUsuario(
            int idUsuario
    ) {

        List<Movimiento> movimientos = new ArrayList<>();

        String sql = "SELECT "
                + "m.id_movimiento, "
                + "m.id_usuario, "
                + "m.id_categoria, "
                + "c.nombre_categoria, "
                + "m.fecha, "
                + "m.tipo_movimiento, "
                + "m.descripcion, "
                + "m.monto "
                + "FROM movimientos m "
                + "INNER JOIN categorias c "
                + "ON m.id_categoria = c.id_categoria "
                + "WHERE m.id_usuario = ? "
                + "ORDER BY m.fecha DESC";

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();
                PreparedStatement consulta =
                        conexion.prepareStatement(sql)
        ) {

            consulta.setInt(1, idUsuario);

            try (
                    ResultSet resultado =
                            consulta.executeQuery()
            ) {

                while (resultado.next()) {

                    Movimiento movimiento = new Movimiento();

                    movimiento.setIdMovimiento(
                            resultado.getInt("id_movimiento")
                    );

                    movimiento.setIdUsuario(
                            resultado.getInt("id_usuario")
                    );

                    movimiento.setIdCategoria(
                            resultado.getInt("id_categoria")
                    );

                    movimiento.setNombreCategoria(
                            resultado.getString("nombre_categoria")
                    );

                    movimiento.setFecha(
                            resultado.getDate("fecha").toString()
                    );

                    movimiento.setTipoMovimiento(
                            resultado.getString("tipo_movimiento")
                    );

                    movimiento.setDescripcion(
                            resultado.getString("descripcion")
                    );

                    movimiento.setMonto(
                            resultado.getBigDecimal("monto")
                    );

                    movimientos.add(movimiento);
                }
            }

        } catch (SQLException error) {

            System.out.println(
                    "Error al consultar movimientos."
            );

            System.out.println(
                    "Detalle: " + error.getMessage()
            );
        }

        return movimientos;
    }

    public Movimiento buscarMovimientoPorId(
            int idMovimiento,
            int idUsuario
    ) {

        String sql = "SELECT "
                + "m.id_movimiento, "
                + "m.id_usuario, "
                + "m.id_categoria, "
                + "c.nombre_categoria, "
                + "m.fecha, "
                + "m.tipo_movimiento, "
                + "m.descripcion, "
                + "m.monto "
                + "FROM movimientos m "
                + "INNER JOIN categorias c "
                + "ON m.id_categoria = c.id_categoria "
                + "WHERE m.id_movimiento = ? "
                + "AND m.id_usuario = ?";

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();
                PreparedStatement consulta =
                        conexion.prepareStatement(sql)
        ) {

            consulta.setInt(1, idMovimiento);
            consulta.setInt(2, idUsuario);

            try (
                    ResultSet resultado =
                            consulta.executeQuery()
            ) {

                if (resultado.next()) {

                    Movimiento movimiento = new Movimiento();

                    movimiento.setIdMovimiento(
                            resultado.getInt("id_movimiento")
                    );

                    movimiento.setIdUsuario(
                            resultado.getInt("id_usuario")
                    );

                    movimiento.setIdCategoria(
                            resultado.getInt("id_categoria")
                    );

                    movimiento.setNombreCategoria(
                            resultado.getString("nombre_categoria")
                    );

                    movimiento.setFecha(
                            resultado.getDate("fecha").toString()
                    );

                    movimiento.setTipoMovimiento(
                            resultado.getString("tipo_movimiento")
                    );

                    movimiento.setDescripcion(
                            resultado.getString("descripcion")
                    );

                    movimiento.setMonto(
                            resultado.getBigDecimal("monto")
                    );

                    return movimiento;
                }
            }

        } catch (SQLException error) {

            System.out.println(
                    "Error al buscar movimiento."
            );

            System.out.println(
                    "Detalle: " + error.getMessage()
            );
        }

        return null;
    }

    public boolean actualizarMovimiento(
            Movimiento movimiento
    ) {

        String sql = "UPDATE movimientos SET "
                + "id_categoria = ?, "
                + "fecha = ?, "
                + "tipo_movimiento = ?, "
                + "descripcion = ?, "
                + "monto = ? "
                + "WHERE id_movimiento = ? "
                + "AND id_usuario = ?";

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();
                PreparedStatement consulta =
                        conexion.prepareStatement(sql)
        ) {

            consulta.setInt(
                    1,
                    movimiento.getIdCategoria()
            );

            consulta.setDate(
                    2,
                    Date.valueOf(movimiento.getFecha())
            );

            consulta.setString(
                    3,
                    movimiento.getTipoMovimiento()
            );

            consulta.setString(
                    4,
                    movimiento.getDescripcion()
            );

            consulta.setBigDecimal(
                    5,
                    movimiento.getMonto()
            );

            consulta.setInt(
                    6,
                    movimiento.getIdMovimiento()
            );

            consulta.setInt(
                    7,
                    movimiento.getIdUsuario()
            );

            int filasAfectadas = consulta.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException error) {

            System.out.println(
                    "Error al actualizar movimiento."
            );

            System.out.println(
                    "Detalle: " + error.getMessage()
            );

            return false;
        }
    }

    public boolean eliminarMovimiento(
            int idMovimiento,
            int idUsuario
    ) {

        String sql = "DELETE FROM movimientos "
                + "WHERE id_movimiento = ? "
                + "AND id_usuario = ?";

        try (
                Connection conexion =
                        ConexionDB.obtenerConexion();
                PreparedStatement consulta =
                        conexion.prepareStatement(sql)
        ) {

            consulta.setInt(1, idMovimiento);
            consulta.setInt(2, idUsuario);

            int filasAfectadas = consulta.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException error) {

            System.out.println(
                    "Error al eliminar movimiento."
            );

            System.out.println(
                    "Detalle: " + error.getMessage()
            );

            return false;
        }
    }
}