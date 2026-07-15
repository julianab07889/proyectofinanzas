package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.Presupuesto;

public class PresupuestoDAO {

    public int insertarPresupuesto(Presupuesto presupuesto) {

        String sql = "INSERT INTO presupuestos "
                + "(id_usuario, id_categoria, monto_limite, periodo, fecha_inicio, fecha_fin, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            consulta.setInt(1, presupuesto.getIdUsuario());
            consulta.setInt(2, presupuesto.getIdCategoria());
            consulta.setBigDecimal(3, presupuesto.getMontoLimite());
            consulta.setString(4, presupuesto.getPeriodo());
            consulta.setDate(5, Date.valueOf(presupuesto.getFechaInicio()));
            consulta.setDate(6, Date.valueOf(presupuesto.getFechaFin()));
            consulta.setString(7, presupuesto.getEstado());

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
            System.out.println("Error al insertar presupuesto.");
            System.out.println("Detalle: " + error.getMessage());
        }

        return 0;
    }

    public void listarPresupuestosPorUsuario(int idUsuario) {

        String sql = "SELECT p.id_presupuesto, p.id_usuario, p.id_categoria, "
                + "c.nombre_categoria, p.monto_limite, p.periodo, "
                + "p.fecha_inicio, p.fecha_fin, p.estado "
                + "FROM presupuestos p "
                + "INNER JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE p.id_usuario = ? "
                + "ORDER BY p.fecha_inicio DESC";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idUsuario);

            ResultSet resultado = consulta.executeQuery();

            System.out.println("");
            System.out.println("=== PRESUPUESTOS DEL USUARIO ID: " + idUsuario + " ===");

            while (resultado.next()) {

                Presupuesto presupuesto = new Presupuesto();

                presupuesto.setIdPresupuesto(resultado.getInt("id_presupuesto"));
                presupuesto.setIdUsuario(resultado.getInt("id_usuario"));
                presupuesto.setIdCategoria(resultado.getInt("id_categoria"));
                presupuesto.setNombreCategoria(resultado.getString("nombre_categoria"));
                presupuesto.setMontoLimite(resultado.getBigDecimal("monto_limite"));
                presupuesto.setPeriodo(resultado.getString("periodo"));
                presupuesto.setFechaInicio(resultado.getDate("fecha_inicio").toString());
                presupuesto.setFechaFin(resultado.getDate("fecha_fin").toString());
                presupuesto.setEstado(resultado.getString("estado"));

                System.out.println("-----------------------------");
                System.out.println("ID Presupuesto: " + presupuesto.getIdPresupuesto());
                System.out.println("Categoria: " + presupuesto.getNombreCategoria());
                System.out.println("Monto limite: " + presupuesto.getMontoLimite());
                System.out.println("Periodo: " + presupuesto.getPeriodo());
                System.out.println("Fecha inicio: " + presupuesto.getFechaInicio());
                System.out.println("Fecha fin: " + presupuesto.getFechaFin());
                System.out.println("Estado: " + presupuesto.getEstado());
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al consultar presupuestos.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }

    public boolean actualizarPresupuesto(Presupuesto presupuesto) {

        String sql = "UPDATE presupuestos SET "
                + "id_categoria = ?, "
                + "monto_limite = ?, "
                + "periodo = ?, "
                + "fecha_inicio = ?, "
                + "fecha_fin = ?, "
                + "estado = ? "
                + "WHERE id_presupuesto = ? AND id_usuario = ?";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, presupuesto.getIdCategoria());
            consulta.setBigDecimal(2, presupuesto.getMontoLimite());
            consulta.setString(3, presupuesto.getPeriodo());
            consulta.setDate(4, Date.valueOf(presupuesto.getFechaInicio()));
            consulta.setDate(5, Date.valueOf(presupuesto.getFechaFin()));
            consulta.setString(6, presupuesto.getEstado());
            consulta.setInt(7, presupuesto.getIdPresupuesto());
            consulta.setInt(8, presupuesto.getIdUsuario());

            int filasAfectadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return filasAfectadas > 0;

        } catch (SQLException error) {
            System.out.println("Error al actualizar presupuesto.");
            System.out.println("Detalle: " + error.getMessage());
            return false;
        }
    }

    public boolean eliminarPresupuesto(int idPresupuesto, int idUsuario) {

        String sql = "DELETE FROM presupuestos WHERE id_presupuesto = ? AND id_usuario = ?";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idPresupuesto);
            consulta.setInt(2, idUsuario);

            int filasAfectadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return filasAfectadas > 0;

        } catch (SQLException error) {
            System.out.println("Error al eliminar presupuesto.");
            System.out.println("Detalle: " + error.getMessage());
            return false;
        }
    }
}