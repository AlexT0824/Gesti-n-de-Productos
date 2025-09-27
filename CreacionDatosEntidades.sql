-- Datos para Usuarios
INSERT INTO usuarios (nombre, apellido, nombre_usuario, correo, clave, roles, estado, bloqueado, intentos_login, fecha_creacion_user, fecha_ultimo_ingreso)
VALUES
('Juan', 'Pérez', 'juanp', 'juan.perez@mail.com', '$2a$10$zX7bG1Yf0Kz1C4wZpWlQO.jQ8fR.1LdSvn9v6sN0vDMeCzvY4bD7C', 'USUARIO', 'ACTIVO', 0, 0, GETDATE(), GETDATE()),
('María', 'González', 'mariag', 'maria.gonzalez@mail.com', '$2a$10$3v2P6YpD2U9w1JkXzBvO5.mD5eR.3KfTvn7v9sM0vFMfYtZ7vE5G', 'USUARIO', 'ACTIVO', 0, 0, GETDATE(), GETDATE()),
('Admin', 'Sistema', 'admin', 'admin@mail.com', '$2a$10$8c6H2QmF7Rz0L2YqWlR2P.nD9fT.5LdTvn8v2sN1vDMhCzvY5bE7', 'ADMIN', 'ACTIVO', 0, 0, GETDATE(), GETDATE());


-- Datos para CategoriaProductos.
INSERT INTO categoria_productos (categorias, nombre, estado, fecha_creacion_producto, fecha_ultima_actualizacion)
VALUES
('ELECTRONICA', 'Celulares', 'ACTIVO', GETDATE(), GETDATE()),
('HOGAR', 'Electrodomésticos', 'ACTIVO', GETDATE(), GETDATE()),
('ALIMENTACION', 'Snacks', 'ACTIVO', GETDATE(), GETDATE());

-- Datos para Productos.
INSERT INTO productos (nombre, categorias, costo, precio, lista_tags, estado, fecha_creacion, fecha_ultima_actualizacion)
VALUES
('iPhone 14', 'ELECTRONICA', 700, 950, 'smartphone,apple,ios', 'ACTIVO', GETDATE(), GETDATE()),
('Televisor LG 55"', 'ELECTRODOMESTICOS', 400, 600, 'tv,led,lg', 'ACTIVO', GETDATE(), GETDATE()),
('Chips de papa', 'ALIMENTACION', 1, 2.5, 'snack,papas,comida', 'ACTIVO', GETDATE(), GETDATE());


DELETE FROM usuarios;
DBCC CHECKIDENT ('usuarios', RESEED, 0);
DELETE FROM categoria_productos;
DBCC CHECKIDENT ('categoria_productos', RESEED, 0);
DELETE FROM productos;
DBCC CHECKIDENT ('productos', RESEED, 0);