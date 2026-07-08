DROP DATABASE IF EXISTS app_finanzas;

CREATE DATABASE app_finanzas
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE app_finanzas;

-- =====================================================
-- TABLA: USUARIOS
-- =====================================================
-- Guarda los usuarios que podrán iniciar sesión en la app.

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(120) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Activo', 'Inactivo') NOT NULL DEFAULT 'Activo'
);

-- =====================================================
-- TABLA: CATEGORIAS
-- =====================================================
-- Clasifica ingresos y gastos.
-- Ejemplo: Salario = Ingreso, Comida = Gasto.

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL,
    tipo_categoria ENUM('Ingreso', 'Gasto') NOT NULL,
    descripcion VARCHAR(200),
    estado ENUM('Activa', 'Inactiva') NOT NULL DEFAULT 'Activa'
);

-- =====================================================
-- TABLA: MOVIMIENTOS
-- =====================================================
-- Guarda ingresos y gastos de cada usuario.

CREATE TABLE movimientos (
    id_movimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_categoria INT NOT NULL,
    fecha DATE NOT NULL,
    tipo_movimiento ENUM('Ingreso', 'Gasto') NOT NULL,
    descripcion VARCHAR(150) NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_movimientos_usuarios
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_movimientos_categorias
        FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT chk_movimientos_monto
        CHECK (monto > 0)
);

-- =====================================================
-- TABLA: METAS FINANCIERAS
-- =====================================================
-- Guarda objetivos financieros del usuario.
-- Ejemplo: Comprar moto, ahorrar para viaje, fondo de emergencia.

CREATE TABLE metas_financieras (
    id_meta INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    nombre_meta VARCHAR(100) NOT NULL,
    monto_objetivo DECIMAL(12,2) NOT NULL,
    monto_actual DECIMAL(12,2) NOT NULL DEFAULT 0,
    fecha_inicio DATE NOT NULL,
    fecha_limite DATE,
    estado ENUM('Activa', 'Cumplida', 'Cancelada') NOT NULL DEFAULT 'Activa',
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_metas_usuarios
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT chk_meta_objetivo
        CHECK (monto_objetivo > 0),

    CONSTRAINT chk_meta_actual
        CHECK (monto_actual >= 0),

    CONSTRAINT chk_meta_no_supera_objetivo
        CHECK (monto_actual <= monto_objetivo)
);

-- =====================================================
-- TABLA: ABONOS A METAS
-- =====================================================
-- Registra cada abono hecho a una meta.
-- Esto es mejor que solo actualizar el monto_actual, porque deja historial.

CREATE TABLE abonos_metas (
    id_abono INT AUTO_INCREMENT PRIMARY KEY,
    id_meta INT NOT NULL,
    fecha_abono DATE NOT NULL,
    monto_abono DECIMAL(12,2) NOT NULL,
    descripcion VARCHAR(150),
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_abonos_metas
        FOREIGN KEY (id_meta)
        REFERENCES metas_financieras(id_meta)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT chk_abono_monto
        CHECK (monto_abono > 0)
);

-- =====================================================
-- TABLA: PRESUPUESTOS
-- =====================================================
-- Define límites de gasto por usuario, categoría y periodo.

CREATE TABLE presupuestos (
    id_presupuesto INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_categoria INT NOT NULL,
    monto_limite DECIMAL(12,2) NOT NULL,
    periodo ENUM('Semanal', 'Mensual', 'Anual') NOT NULL DEFAULT 'Mensual',
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado ENUM('Activo', 'Finalizado', 'Cancelado') NOT NULL DEFAULT 'Activo',
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_presupuestos_usuarios
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_presupuestos_categorias
        FOREIGN KEY (id_categoria)
        REFERENCES categorias(id_categoria)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    CONSTRAINT chk_presupuesto_monto
        CHECK (monto_limite > 0),

    CONSTRAINT chk_presupuesto_fechas
        CHECK (fecha_fin >= fecha_inicio)
);

-- =====================================================
-- ÍNDICES
-- =====================================================
-- Ayudan a que las búsquedas sean más rápidas.
-- Esto es buena práctica. La app no se arrastra como computador con 40 pestañas.

CREATE INDEX idx_movimientos_usuario ON movimientos(id_usuario);
CREATE INDEX idx_movimientos_categoria ON movimientos(id_categoria);
CREATE INDEX idx_movimientos_fecha ON movimientos(fecha);

CREATE INDEX idx_metas_usuario ON metas_financieras(id_usuario);
CREATE INDEX idx_presupuestos_usuario ON presupuestos(id_usuario);
CREATE INDEX idx_presupuestos_categoria ON presupuestos(id_categoria);
CREATE INDEX idx_presupuestos_fechas ON presupuestos(fecha_inicio, fecha_fin);

-- =====================================================
-- DATOS INICIALES: CATEGORÍAS
-- =====================================================

INSERT INTO categorias (nombre_categoria, tipo_categoria, descripcion)
VALUES
('Salario', 'Ingreso', 'Ingresos por trabajo o pagos recibidos'),
('Freelance', 'Ingreso', 'Ingresos por trabajos independientes'),
('Bonificación', 'Ingreso', 'Ingresos adicionales o premios'),
('Otros ingresos', 'Ingreso', 'Ingresos no clasificados'),

('Comida', 'Gasto', 'Gastos de alimentación y mercado'),
('Transporte', 'Gasto', 'Gastos de transporte público, gasolina o movilidad'),
('Servicios', 'Gasto', 'Pagos de servicios públicos o privados'),
('Arriendo', 'Gasto', 'Pago de vivienda'),
('Entretenimiento', 'Gasto', 'Gastos de ocio, salidas y diversión'),
('Educación', 'Gasto', 'Gastos de estudio o formación'),
('Salud', 'Gasto', 'Gastos médicos o relacionados'),
('Ropa', 'Gasto', 'Compra de ropa o accesorios'),
('Deudas', 'Gasto', 'Pagos de créditos, préstamos o tarjetas'),
('Otros gastos', 'Gasto', 'Gastos no clasificados');

-- =====================================================
-- USUARIO DE PRUEBA
-- =====================================================
-- Para probar login:
-- correo: alejandro@gmail.com
-- contraseña: 1234

INSERT INTO usuarios (nombre, correo, contrasena)
VALUES ('Alejandro', 'alejandro@gmail.com', '1234');

-- =====================================================
-- MOVIMIENTOS DE PRUEBA
-- =====================================================

INSERT INTO movimientos 
(id_usuario, id_categoria, fecha, tipo_movimiento, descripcion, monto)
VALUES
(1, 1, CURDATE(), 'Ingreso', 'Pago de salario mensual', 2800000),
(1, 5, CURDATE(), 'Gasto', 'Compra de mercado', 250000),
(1, 6, CURDATE(), 'Gasto', 'Transporte semanal', 80000),
(1, 7, CURDATE(), 'Gasto', 'Pago de internet', 120000);

-- =====================================================
-- META DE PRUEBA
-- =====================================================

INSERT INTO metas_financieras 
(id_usuario, nombre_meta, monto_objetivo, monto_actual, fecha_inicio, fecha_limite, estado)
VALUES
(1, 'Fondo de emergencia', 3000000, 500000, CURDATE(), '2026-12-31', 'Activa');

-- =====================================================
-- PRESUPUESTOS DE PRUEBA
-- =====================================================

INSERT INTO presupuestos
(id_usuario, id_categoria, monto_limite, periodo, fecha_inicio, fecha_fin)
VALUES
(1, 5, 600000, 'Mensual', DATE_FORMAT(CURDATE(), '%Y-%m-01'), LAST_DAY(CURDATE())),
(1, 6, 250000, 'Mensual', DATE_FORMAT(CURDATE(), '%Y-%m-01'), LAST_DAY(CURDATE())),
(1, 7, 300000, 'Mensual', DATE_FORMAT(CURDATE(), '%Y-%m-01'), LAST_DAY(CURDATE()));

-- =====================================================
-- VISTA: RESUMEN FINANCIERO POR USUARIO
-- =====================================================
-- Sirve para dashboard: ingresos, gastos y saldo.

CREATE VIEW vista_resumen_usuario AS
SELECT 
    u.id_usuario,
    u.nombre,
    u.correo,
    COALESCE(SUM(CASE WHEN m.tipo_movimiento = 'Ingreso' THEN m.monto ELSE 0 END), 0) AS total_ingresos,
    COALESCE(SUM(CASE WHEN m.tipo_movimiento = 'Gasto' THEN m.monto ELSE 0 END), 0) AS total_gastos,
    COALESCE(SUM(CASE WHEN m.tipo_movimiento = 'Ingreso' THEN m.monto ELSE 0 END), 0) -
    COALESCE(SUM(CASE WHEN m.tipo_movimiento = 'Gasto' THEN m.monto ELSE 0 END), 0) AS saldo_actual
FROM usuarios u
LEFT JOIN movimientos m ON u.id_usuario = m.id_usuario
GROUP BY u.id_usuario, u.nombre, u.correo;

-- =====================================================
-- VISTA: GASTOS POR CATEGORÍA
-- =====================================================
-- Sirve para reportes.

CREATE VIEW vista_gastos_por_categoria AS
SELECT
    u.id_usuario,
    u.nombre,
    c.nombre_categoria,
    SUM(m.monto) AS total_gastado
FROM movimientos m
INNER JOIN usuarios u ON m.id_usuario = u.id_usuario
INNER JOIN categorias c ON m.id_categoria = c.id_categoria
WHERE m.tipo_movimiento = 'Gasto'
GROUP BY u.id_usuario, u.nombre, c.nombre_categoria;

-- =====================================================
-- VISTA: ESTADO DE PRESUPUESTOS
-- =====================================================
-- Compara presupuesto contra gasto real.

CREATE VIEW vista_estado_presupuestos AS
SELECT
    p.id_presupuesto,
    u.id_usuario,
    u.nombre AS usuario,
    c.nombre_categoria,
    p.monto_limite,
    p.periodo,
    p.fecha_inicio,
    p.fecha_fin,
    COALESCE(SUM(m.monto), 0) AS total_gastado,
    p.monto_limite - COALESCE(SUM(m.monto), 0) AS disponible,
    ROUND((COALESCE(SUM(m.monto), 0) / p.monto_limite) * 100, 2) AS porcentaje_usado
FROM presupuestos p
INNER JOIN usuarios u ON p.id_usuario = u.id_usuario
INNER JOIN categorias c ON p.id_categoria = c.id_categoria
LEFT JOIN movimientos m 
    ON m.id_usuario = p.id_usuario
    AND m.id_categoria = p.id_categoria
    AND m.tipo_movimiento = 'Gasto'
    AND m.fecha BETWEEN p.fecha_inicio AND p.fecha_fin
GROUP BY
    p.id_presupuesto,
    u.id_usuario,
    u.nombre,
    c.nombre_categoria,
    p.monto_limite,
    p.periodo,
    p.fecha_inicio,
    p.fecha_fin;

-- =====================================================
-- CONSULTAS DE VERIFICACIÓN
-- =====================================================

SHOW TABLES;

SELECT * FROM usuarios;

SELECT * FROM categorias;

SELECT * FROM vista_resumen_usuario;

SELECT * FROM vista_gastos_por_categoria;

SELECT * FROM vista_estado_presupuestos;