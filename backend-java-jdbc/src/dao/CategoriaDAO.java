package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Categoria;

public class CategoriaDAO {

    public void listarCategorias() {

        String sql = "SELECT id_categoria, nombre_categoria, tipo_categoria, descripcion, estado "
                + "FROM categorias "
                + "ORDER BY tipo_categoria, nombre_categoria";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            System.out.println("");
            System.out.println("=== LISTADO DE CATEGORIAS ===");

            while (resultado.next()) {

                Categoria categoria = new Categoria();

                categoria.setIdCategoria(resultado.getInt("id_categoria"));
                categoria.setNombreCategoria(resultado.getString("nombre_categoria"));
                categoria.setTipoCategoria(resultado.getString("tipo_categoria"));
                categoria.setDescripcion(resultado.getString("descripcion"));
                categoria.setEstado(resultado.getString("estado"));

                System.out.println("-----------------------------");
                System.out.println("ID Categoria: " + categoria.getIdCategoria());
                System.out.println("Nombre: " + categoria.getNombreCategoria());
                System.out.println("Tipo: " + categoria.getTipoCategoria());
                System.out.println("Descripcion: " + categoria.getDescripcion());
                System.out.println("Estado: " + categoria.getEstado());
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al consultar categorias.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }

    public void listarCategoriasPorTipo(String tipoCategoria) {

        String sql = "SELECT id_categoria, nombre_categoria, tipo_categoria, descripcion, estado "
                + "FROM categorias "
                + "WHERE tipo_categoria = ? AND estado = 'Activa' "
                + "ORDER BY nombre_categoria";

        try {
            Connection conexion = ConexionDB.conectar();

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, tipoCategoria);

            ResultSet resultado = consulta.executeQuery();

            System.out.println("");
            System.out.println("=== CATEGORIAS DE TIPO: " + tipoCategoria + " ===");

            while (resultado.next()) {

                Categoria categoria = new Categoria();

                categoria.setIdCategoria(resultado.getInt("id_categoria"));
                categoria.setNombreCategoria(resultado.getString("nombre_categoria"));
                categoria.setTipoCategoria(resultado.getString("tipo_categoria"));
                categoria.setDescripcion(resultado.getString("descripcion"));
                categoria.setEstado(resultado.getString("estado"));

                System.out.println("-----------------------------");
                System.out.println("ID Categoria: " + categoria.getIdCategoria());
                System.out.println("Nombre: " + categoria.getNombreCategoria());
                System.out.println("Tipo: " + categoria.getTipoCategoria());
                System.out.println("Descripcion: " + categoria.getDescripcion());
                System.out.println("Estado: " + categoria.getEstado());
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException error) {
            System.out.println("Error al consultar categorias por tipo.");
            System.out.println("Detalle: " + error.getMessage());
        }
    }
}