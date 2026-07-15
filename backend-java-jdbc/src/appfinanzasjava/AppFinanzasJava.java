package appfinanzasjava;

import dao.PresupuestoDAO;
import modelo.Presupuesto;
import java.math.BigDecimal;
import dao.MetaFinancieraDAO;
import modelo.MetaFinanciera;
import dao.CategoriaDAO;
import dao.MovimientoDAO;
import dao.UsuarioDAO;
import java.math.BigDecimal;
import modelo.Movimiento;
import modelo.Usuario;


public class AppFinanzasJava {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String correo = "alejandro@gmail.com";
        String contrasena = "1234";

        Usuario usuario = usuarioDAO.obtenerUsuarioPorLogin(correo, contrasena);

        if (usuario != null) {

            System.out.println("Login correcto.");
            System.out.println("ID usuario: " + usuario.getIdUsuario());
            System.out.println("Nombre: " + usuario.getNombre());
            System.out.println("Correo: " + usuario.getCorreo());
            System.out.println("Estado: " + usuario.getEstado());
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            MetaFinancieraDAO metaDAO = new MetaFinancieraDAO();
PresupuestoDAO presupuestoDAO = new PresupuestoDAO();

System.out.println("");
System.out.println("=== CONSULTA INICIAL DE PRESUPUESTOS ===");
presupuestoDAO.listarPresupuestosPorUsuario(usuario.getIdUsuario());

Presupuesto nuevoPresupuesto = new Presupuesto();

nuevoPresupuesto.setIdUsuario(usuario.getIdUsuario());
nuevoPresupuesto.setIdCategoria(5);
nuevoPresupuesto.setMontoLimite(new BigDecimal("700000"));
nuevoPresupuesto.setPeriodo("Mensual");
nuevoPresupuesto.setFechaInicio("2026-07-01");
nuevoPresupuesto.setFechaFin("2026-07-31");
nuevoPresupuesto.setEstado("Activo");

int idPresupuestoGenerado = presupuestoDAO.insertarPresupuesto(nuevoPresupuesto);

if (idPresupuestoGenerado > 0) {
    System.out.println("");
    System.out.println("Presupuesto insertado correctamente.");
    System.out.println("ID presupuesto generado: " + idPresupuestoGenerado);
} else {
    System.out.println("");
    System.out.println("No se pudo insertar el presupuesto.");
}

System.out.println("");
System.out.println("=== CONSULTA DESPUES DE INSERTAR PRESUPUESTO ===");
presupuestoDAO.listarPresupuestosPorUsuario(usuario.getIdUsuario());

if (idPresupuestoGenerado > 0) {

    Presupuesto presupuestoActualizado = new Presupuesto();

    presupuestoActualizado.setIdPresupuesto(idPresupuestoGenerado);
    presupuestoActualizado.setIdUsuario(usuario.getIdUsuario());
    presupuestoActualizado.setIdCategoria(5);
    presupuestoActualizado.setMontoLimite(new BigDecimal("850000"));
    presupuestoActualizado.setPeriodo("Mensual");
    presupuestoActualizado.setFechaInicio("2026-07-01");
    presupuestoActualizado.setFechaFin("2026-07-31");
    presupuestoActualizado.setEstado("Activo");

    boolean actualizado = presupuestoDAO.actualizarPresupuesto(presupuestoActualizado);

    if (actualizado) {
        System.out.println("");
        System.out.println("Presupuesto actualizado correctamente.");
    } else {
        System.out.println("");
        System.out.println("No se pudo actualizar el presupuesto.");
    }

    System.out.println("");
    System.out.println("=== CONSULTA DESPUES DE ACTUALIZAR PRESUPUESTO ===");
    presupuestoDAO.listarPresupuestosPorUsuario(usuario.getIdUsuario());

    boolean eliminado = presupuestoDAO.eliminarPresupuesto(idPresupuestoGenerado, usuario.getIdUsuario());

    if (eliminado) {
        System.out.println("");
        System.out.println("Presupuesto eliminado correctamente.");
    } else {
        System.out.println("");
        System.out.println("No se pudo eliminar el presupuesto.");
    }

    System.out.println("");
    System.out.println("=== CONSULTA DESPUES DE ELIMINAR PRESUPUESTO ===");
    presupuestoDAO.listarPresupuestosPorUsuario(usuario.getIdUsuario());
}
System.out.println("");
System.out.println("=== CONSULTA INICIAL DE METAS ===");
metaDAO.listarMetasPorUsuario(usuario.getIdUsuario());

MetaFinanciera nuevaMeta = new MetaFinanciera();

nuevaMeta.setIdUsuario(usuario.getIdUsuario());
nuevaMeta.setNombreMeta("Meta de prueba desde Java");
nuevaMeta.setMontoObjetivo(new BigDecimal("1000000"));
nuevaMeta.setMontoActual(new BigDecimal("200000"));
nuevaMeta.setFechaInicio("2026-07-14");
nuevaMeta.setFechaLimite("2026-12-31");
nuevaMeta.setEstado("Activa");

int idMetaGenerada = metaDAO.insertarMeta(nuevaMeta);

if (idMetaGenerada > 0) {
    System.out.println("");
    System.out.println("Meta insertada correctamente.");
    System.out.println("ID meta generada: " + idMetaGenerada);
} else {
    System.out.println("");
    System.out.println("No se pudo insertar la meta.");
}

System.out.println("");
System.out.println("=== CONSULTA DESPUES DE INSERTAR META ===");
metaDAO.listarMetasPorUsuario(usuario.getIdUsuario());

if (idMetaGenerada > 0) {

    MetaFinanciera metaActualizada = new MetaFinanciera();

    metaActualizada.setIdMeta(idMetaGenerada);
    metaActualizada.setIdUsuario(usuario.getIdUsuario());
    metaActualizada.setNombreMeta("Meta de prueba actualizada desde Java");
    metaActualizada.setMontoObjetivo(new BigDecimal("1200000"));
    metaActualizada.setMontoActual(new BigDecimal("300000"));
    metaActualizada.setFechaInicio("2026-07-14");
    metaActualizada.setFechaLimite("2026-12-31");
    metaActualizada.setEstado("Activa");

    boolean actualizada = metaDAO.actualizarMeta(metaActualizada);

    if (actualizada) {
        System.out.println("");
        System.out.println("Meta actualizada correctamente.");
    } else {
        System.out.println("");
        System.out.println("No se pudo actualizar la meta.");
    }

    System.out.println("");
    System.out.println("=== CONSULTA DESPUES DE ACTUALIZAR META ===");
    metaDAO.listarMetasPorUsuario(usuario.getIdUsuario());

    boolean eliminada = metaDAO.eliminarMeta(idMetaGenerada, usuario.getIdUsuario());

    if (eliminada) {
        System.out.println("");
        System.out.println("Meta eliminada correctamente.");
    } else {
        System.out.println("");
        System.out.println("No se pudo eliminar la meta.");
    }

    System.out.println("");
    System.out.println("=== CONSULTA DESPUES DE ELIMINAR META ===");
    metaDAO.listarMetasPorUsuario(usuario.getIdUsuario());
}

categoriaDAO.listarCategorias();

categoriaDAO.listarCategoriasPorTipo("Ingreso");

categoriaDAO.listarCategoriasPorTipo("Gasto");

            MovimientoDAO movimientoDAO = new MovimientoDAO();

            System.out.println("");
            System.out.println("=== CONSULTA INICIAL ===");
            movimientoDAO.listarMovimientosPorUsuario(usuario.getIdUsuario());

            Movimiento nuevoMovimiento = new Movimiento();

            nuevoMovimiento.setIdUsuario(usuario.getIdUsuario());
            nuevoMovimiento.setIdCategoria(5);
            nuevoMovimiento.setFecha("2026-07-14");
            nuevoMovimiento.setTipoMovimiento("Gasto");
            nuevoMovimiento.setDescripcion("Compra de prueba desde Java");
            nuevoMovimiento.setMonto(new BigDecimal("45000"));

            int idMovimientoGenerado = movimientoDAO.insertarMovimiento(nuevoMovimiento);

            if (idMovimientoGenerado > 0) {
                System.out.println("");
                System.out.println("Movimiento insertado correctamente.");
                System.out.println("ID generado: " + idMovimientoGenerado);
            } else {
                System.out.println("");
                System.out.println("No se pudo insertar el movimiento.");
            }

            System.out.println("");
            System.out.println("=== CONSULTA DESPUES DE INSERTAR ===");
            movimientoDAO.listarMovimientosPorUsuario(usuario.getIdUsuario());

            if (idMovimientoGenerado > 0) {

                int idMovimientoPrueba = idMovimientoGenerado;

                Movimiento movimientoActualizado = new Movimiento();

                movimientoActualizado.setIdMovimiento(idMovimientoPrueba);
                movimientoActualizado.setIdUsuario(usuario.getIdUsuario());
                movimientoActualizado.setIdCategoria(5);
                movimientoActualizado.setFecha("2026-07-14");
                movimientoActualizado.setTipoMovimiento("Gasto");
                movimientoActualizado.setDescripcion("Compra de prueba actualizada desde Java");
                movimientoActualizado.setMonto(new BigDecimal("60000"));

                boolean actualizado = movimientoDAO.actualizarMovimiento(movimientoActualizado);

                if (actualizado) {
                    System.out.println("");
                    System.out.println("Movimiento actualizado correctamente.");
                } else {
                    System.out.println("");
                    System.out.println("No se pudo actualizar el movimiento.");
                }

                System.out.println("");
                System.out.println("=== CONSULTA DESPUES DE ACTUALIZAR ===");
                movimientoDAO.listarMovimientosPorUsuario(usuario.getIdUsuario());

                boolean eliminado = movimientoDAO.eliminarMovimiento(idMovimientoPrueba, usuario.getIdUsuario());

                if (eliminado) {
                    System.out.println("");
                    System.out.println("Movimiento eliminado correctamente.");
                } else {
                    System.out.println("");
                    System.out.println("No se pudo eliminar el movimiento.");
                }

                System.out.println("");
                System.out.println("=== CONSULTA DESPUES DE ELIMINAR ===");
                movimientoDAO.listarMovimientosPorUsuario(usuario.getIdUsuario());
            }

        } else {
            System.out.println("Correo o contrasena incorrectos.");
        }
    }
}