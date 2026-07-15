package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modelo.MetaFinanciera;

public class MetaFinancieraDAO {

    public int insertarMeta(MetaFinanciera meta) {

        String sql = "INSERT INTO metas_financieras "
                + "(id_usuario, nombre_meta, monto_objetivo, monto_actual, fecha_inicio, fecha_limite, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            consulta.setInt(1, meta.getIdUsuario());
            consulta.setString(2, meta.getNombreMeta());
            consulta.setBigDecimal(3, meta.getMontoObjetivo());
            consulta.setBigDecimal(4, meta.getMontoActual());
            consulta.setDate(5, Date.valueOf(meta.getFechaInicio()));
            consulta.setDate(6, Date.valueOf(meta.getFechaLimite()));
            consulta.setString(7, meta.getEstado());

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
            System.out.println("Error al insertar meta financiera.");
            System.out.println("Detalle: " + error.getMessage());
        }

        return 0;
    }

    public void listarMetasPorUsuario(int idUsuario) {

        String sql = "SELECT id_meta, id_usuario, nombre_meta, monto_objetivo, monto_actual, "
                + "fecha_inicio, fecha_limite, estado "
                + "FROM metas_financieras "
                + "WHERE id_usuario = ? "
                + "ORDER BY fecha_limite ASC";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idUsuario);

            ResultSet resultado = consulta.executeQuery();

            System.out.println("");
            System.out.println("=== METAS FINANCIERAS DEL USUARIO ID: " + idUsuario + " ===");

            while (resultado.next()) {

                MetaFinanciera meta = new MetaFinanciera();

                meta.setIdMeta(resultado.getInt("id_meta"));
                meta.setIdUsuario(resultado.getInt("id_usuario"));
                meta.setNombreMeta(resultado.getString("nombre_meta"));
                meta.setMontoObjetivo(resultado.getBigDecimal("monto_objetivo"));
                meta.setMontoActual(resultado.getBigDecimal("monto_actual"));
                meta.setFechaInicio(resultado.getDate("fecha_inicio").toString());
                meta.setFechaLimite(resultado.getDate("fecha_limite").toString());
                meta.setEstado(resultado.getString("estado"));

                System.out.println("-----------------------------");
                System.out.println("ID Meta: " + meta.getIdMeta());
                System.out.println("Nombre: " + meta.getNombreMeta());
                System.out.println("Monto objetivo: " + meta.getMontoObjetivo());
                System.out.println("Monto actual: " + meta.getMontoActual());
                System.out.println("Fecha inicio: " + meta.getFechaInicio());
                System.out.println("Fecha limite: " + meta.getFechaLimite());
                System.out.println("Estado: " + meta.getEstado());
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al consultar metas financieras.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }

    public boolean actualizarMeta(MetaFinanciera meta) {

        String sql = "UPDATE metas_financieras SET "
                + "nombre_meta = ?, "
                + "monto_objetivo = ?, "
                + "monto_actual = ?, "
                + "fecha_inicio = ?, "
                + "fecha_limite = ?, "
                + "estado = ? "
                + "WHERE id_meta = ? AND id_usuario = ?";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, meta.getNombreMeta());
            consulta.setBigDecimal(2, meta.getMontoObjetivo());
            consulta.setBigDecimal(3, meta.getMontoActual());
            consulta.setDate(4, Date.valueOf(meta.getFechaInicio()));
            consulta.setDate(5, Date.valueOf(meta.getFechaLimite()));
            consulta.setString(6, meta.getEstado());
            consulta.setInt(7, meta.getIdMeta());
            consulta.setInt(8, meta.getIdUsuario());

            int filasAfectadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return filasAfectadas > 0;

        } catch (SQLException error) {
            System.out.println("Error al actualizar meta financiera.");
            System.out.println("Detalle: " + error.getMessage());
            return false;
        }
    }

    public boolean eliminarMeta(int idMeta, int idUsuario) {

        String sql = "DELETE FROM metas_financieras WHERE id_meta = ? AND id_usuario = ?";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idMeta);
            consulta.setInt(2, idUsuario);

            int filasAfectadas = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return filasAfectadas > 0;

        } catch (SQLException error) {
            System.out.println("Error al eliminar meta financiera.");
            System.out.println("Detalle: " + error.getMessage());
            return false;
        }
    }
}