package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Movimiento;

public class MovimientoDAO {

    public int insertarMovimiento(Movimiento movimiento) {

        String sql = "INSERT INTO movimientos "
                + "(id_usuario, id_categoria, fecha, tipo_movimiento, descripcion, monto) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            consulta.setInt(1, movimiento.getIdUsuario());
            consulta.setInt(2, movimiento.getIdCategoria());
            consulta.setDate(3, Date.valueOf(movimiento.getFecha()));
            consulta.setString(4, movimiento.getTipoMovimiento());
            consulta.setString(5, movimiento.getDescripcion());
            consulta.setBigDecimal(6, movimiento.getMonto());

            int filasAfectadas = consulta.executeUpdate();

            if (filasAfectadas > 0) {

                ResultSet clavesGeneradas = consulta.getGeneratedKeys();

                if (clavesGeneradas.next()) {
                    int idGenerado = clavesGeneradas.getInt(1);

                    clavesGeneradas.close();
                    consulta.close();
                    conexion.close();

                    return idGenerado;
                }

                clavesGeneradas.close();
            }

            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al insertar movimiento.");
            System.out.println("Detalle: " + error.getMessage());
        }

        return 0;
    }

    public void listarMovimientosPorUsuario(int idUsuario) {

        String sql = "SELECT m.id_movimiento, m.id_usuario, m.id_categoria, "
                + "c.nombre_categoria, m.fecha, m.tipo_movimiento, "
                + "m.descripcion, m.monto "
                + "FROM movimientos m "
                + "INNER JOIN categorias c ON m.id_categoria = c.id_categoria "
                + "WHERE m.id_usuario = ? "
                + "ORDER BY m.fecha DESC";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idUsuario);

            ResultSet resultado = consulta.executeQuery();

            System.out.println("");
            System.out.println("MOVIMIENTOS DEL USUARIO ID: " + idUsuario);

            while (resultado.next()) {

                Movimiento movimiento = new Movimiento();

                movimiento.setIdMovimiento(resultado.getInt("id_movimiento"));
                movimiento.setIdUsuario(resultado.getInt("id_usuario"));
                movimiento.setIdCategoria(resultado.getInt("id_categoria"));
                movimiento.setNombreCategoria(resultado.getString("nombre_categoria"));
                movimiento.setFecha(resultado.getDate("fecha").toString());
                movimiento.setTipoMovimiento(resultado.getString("tipo_movimiento"));
                movimiento.setDescripcion(resultado.getString("descripcion"));
                movimiento.setMonto(resultado.getBigDecimal("monto"));

                System.out.println("-----------------------------");
                System.out.println("ID Movimiento: " + movimiento.getIdMovimiento());
                System.out.println("Fecha: " + movimiento.getFecha());
                System.out.println("Tipo: " + movimiento.getTipoMovimiento());
                System.out.println("Categoria: " + movimiento.getNombreCategoria());
                System.out.println("Descripcion: " + movimiento.getDescripcion());
                System.out.println("Monto: " + movimiento.getMonto());
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al consultar movimientos del usuario.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }

    public boolean actualizarMovimiento(Movimiento movimiento) {

        String sql = "UPDATE movimientos SET "
                + "id_categoria = ?, "
                + "fecha = ?, "
                + "tipo_movimiento = ?, "
                + "descripcion = ?, "
                + "monto = ? "
                + "WHERE id_movimiento = ? AND id_usuario = ?";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, movimiento.getIdCategoria());
            consulta.setDate(2, Date.valueOf(movimiento.getFecha()));
            consulta.setString(3, movimiento.getTipoMovimiento());
            consulta.setString(4, movimiento.getDescripcion());
            consulta.setBigDecimal(5, movimiento.getMonto());
            consulta.setInt(6, movimiento.getIdMovimiento());
            consulta.setInt(7, movimiento.getIdUsuario());

            int filasAfectadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return filasAfectadas > 0;

        } catch (SQLException error) {
            System.out.println("Error al actualizar movimiento.");
            System.out.println("Detalle: " + error.getMessage());
            return false;
        }
    }

    public boolean eliminarMovimiento(int idMovimiento, int idUsuario) {

        String sql = "DELETE FROM movimientos WHERE id_movimiento = ? AND id_usuario = ?";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idMovimiento);
            consulta.setInt(2, idUsuario);

            int filasAfectadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return filasAfectadas > 0;

        } catch (SQLException error) {
            System.out.println("Error al eliminar movimiento.");
            System.out.println("Detalle: " + error.getMessage());
            return false;
        }
    }
}