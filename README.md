# Sistema de Gestión de Productos

Este es un sistema de gestión de productos desarrollado con Spring Boot que permite administrar productos, categorías y usuarios con autenticación JWT.

## Configuración inicial

Para ejecutar el proyecto necesitas:
- Java 8 o superior
- Maven 3.6+
- SQL Server

Pasos para ejecutar:
1. Clona el repositorio
2. Configura la conexión a la base de datos en `application.properties`
3. Ejecuta: `mvn spring-boot:run`

## Dependencias

El proyecto usa las siguientes dependencias principales:

### Spring Boot
- `spring-boot-starter-web` - Para crear aplicaciones web REST
- `spring-boot-starter-data-jpa` - Para acceso a datos con JPA
- `spring-boot-starter-security` - Para autenticación y autorización
- `spring-boot-starter-validation` - Para validación de datos
- `spring-boot-starter-test` - Para testing
- `spring-boot-devtools` - Para desarrollo

### Base de datos
- `mssql-jdbc` - Driver para SQL Server

### Utilidades
- `lombok` - Para reducir código boilerplate
- `jjwt` - Para manejo de tokens JWT

### Versiones
- Spring Boot: 2.7.13
- Java: 1.8
- Lombok: 1.18.30
- JWT: 0.9.1

## Autenticación

El sistema usa JWT para la autenticación. Al iniciar la aplicación se crea automáticamente un usuario administrador:

- Correo: admin@tuapp.com
- Contraseña: Admin123@

### Login
Para iniciar sesión usa el endpoint:
```
POST /api/auth/login
```

Con el body:
```json
{
    "correo": "admin@tuapp.com",
    "clave": "Admin123@"
}
```

La respuesta incluye un token JWT que debes usar en todas las peticiones posteriores:
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Usar el token
Incluye el token en el header Authorization:
```
Authorization: Bearer [tu_token]
```

Nota: El token expira en 10 minutos.

## Endpoints de la API

### Autenticación
- `POST /api/auth/login` - Iniciar sesión

### Usuarios (Solo ADMIN)
- `GET /api/usuarios/todos` - Listar todos los usuarios
- `GET /api/usuarios/{id}` - Obtener usuario por ID
- `GET /api/usuarios/nombre?nombre=Juan` - Buscar por nombre
- `GET /api/usuarios/apellido?apellido=Pérez` - Buscar por apellido
- `GET /api/usuarios/nombre-usuario?nombreUsuario=juanp` - Buscar por nombre de usuario
- `GET /api/usuarios/correo?correo=test@example.com` - Buscar por correo
- `GET /api/usuarios/rango-fecha-creacion?inicio=2024-01-01&fin=2024-12-31` - Buscar por rango de fecha
- `GET /api/usuarios/rango-ultimo-ingreso?fechaUltimoIngreso=2024-01-01` - Buscar por último ingreso
- `POST /api/usuarios/crear?correo=test@example.com` - Crear nuevo usuario
- `PUT /api/usuarios/actualizar/{id}` - Actualizar usuario
- `PUT /api/usuarios/cambiar-clave/{id}` - Cambiar contraseña
- `PUT /api/usuarios/desactivar/id/{id}` - Desactivar usuario por ID
- `PUT /api/usuarios/desactivar/nombre-usuario?nombreUsuario=juanp` - Desactivar por nombre usuario
- `PUT /api/usuarios/desactivar/correo?correo=test@example.com` - Desactivar por correo

### Productos (ADMIN/USUARIO)
- `GET /api/productos/{id}` - Obtener producto por ID
- `GET /api/productos/nombre?nombre=iPhone` - Buscar por nombre
- `GET /api/productos/estado?estado=ACTIVO` - Buscar por estado
- `GET /api/productos/rango-fecha-creacion?inicio=2024-01-01&fin=2024-12-31` - Buscar por rango de fecha
- `GET /api/productos/rango-fecha-actualizacion?fechaUltimaActualizacion=2024-01-01` - Buscar por fecha actualización
- `GET /api/productos/categoria?categoria=ELECTRONICA` - Buscar por categoría
- `GET /api/productos/precio?min=100&max=500` - Buscar por rango de precio
- `POST /api/productos/crear` - Crear producto
- `PUT /api/productos/actualizar/{id}` - Actualizar producto
- `PUT /api/productos/desactivar/{id}` - Desactivar producto

### Categorías (ADMIN/USUARIO)
- `GET /api/categorias/{id}` - Obtener categoría por ID
- `GET /api/categorias/buscar?nombre=Smartphones&categoria=ELECTRONICA` - Buscar por nombre y categoría
- `GET /api/categorias/categorias?categoria=ELECTRONICA` - Buscar por categoría
- `GET /api/categorias/estado?estado=ACTIVO` - Buscar por estado
- `POST /api/categorias/crear` - Crear categoría
- `PUT /api/categorias/actualizar/{id}` - Actualizar categoría
- `PUT /api/categorias/desactivar/{id}` - Desactivar categoría

### Consultas (ADMIN/USUARIO)
- `GET /api/consulta/productos` - Listar todos los productos
- `GET /api/consulta/productos/nombre?nombre=iPhone` - Buscar producto por nombre
- `GET /api/consulta/productos/categoria?categoria=ELECTRONICA` - Buscar producto por categoría
- `GET /api/consulta/productos/estado?estado=ACTIVO` - Buscar producto por estado
- `GET /api/consulta/productos/precio?min=100&max=500` - Buscar producto por rango precio
- `GET /api/consulta/categorias` - Listar todas las categorías
- `GET /api/consulta/categorias/nombre?categorias=ELECTRONICA` - Buscar categoría por nombre
- `GET /api/consulta/categorias/estado?estado=ACTIVO` - Buscar categoría por estado

## Configuración en Postman

Para probar la API puedes configurar Postman de la siguiente manera:

1. Crea un Environment con estas variables:
   - `base_url`: `http://localhost:8080`
   - `token`: (se llenará automáticamente)

2. Para el login, usa este script en Tests:
```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("token", response.token);
}
```

3. En cada request que requiera autenticación, configura:
   - Authorization: Bearer Token
   - Token: `{{token}}`

## Ejemplos de uso

### Crear Usuario
POST `/api/usuarios/crear?correo=test@example.com`
```json
{
    "nombre": "Juan",
    "apellido": "Pérez", 
    "nombreUsuario": "juanp",
    "correo": "test@example.com",
    "clave": "MiPassword123@",
    "roles": "USUARIO"
}
```

### Actualizar Usuario
PUT `/api/usuarios/actualizar/1`
```json
{
    "nombre": "Juan Carlos",
    "apellido": "Pérez García",
    "nombreUsuario": "juancarlos",
    "correo": "juan.nuevo@example.com",
    "roles": "ADMIN"
}
```

### Crear Producto
POST `/api/productos/crear`
```json
{
    "nombre": "iPhone 15 Pro",
    "categorias": "ELECTRONICA",
    "costo": 800.0,
    "precio": 1200.0,
    "listaTags": "smartphone,apple,ios,premium",
    "estado": "ACTIVO"
}
```

### Actualizar Producto
PUT `/api/productos/actualizar/1`
```json
{
    "nombre": "iPhone 15 Pro Max",
    "categorias": "ELECTRONICA",
    "costo": 900.0,
    "precio": 1300.0,
    "listaTags": "smartphone,apple,ios,premium,max",
    "estado": "ACTIVO"
}
```

### Crear Categoría
POST `/api/categorias/crear`
```json
{
    "categorias": "ELECTRONICA",
    "nombre": "Smartphones Premium",
    "estado": "ACTIVO"
}
```

### Actualizar Categoría
PUT `/api/categorias/actualizar/1`
```json
{
    "categorias": "ELECTRONICA",
    "nombre": "Dispositivos Móviles Premium",
    "estado": "ACTIVO"
}
```

## Valores válidos

### Categorías
ELECTRONICA, ROPA, ALIMENTACION, HOGAR, JUGUETES, DEPORTES, LIBROS, OTROS

### Estados
ACTIVO, DESACTIVO, BORRADO

### Roles
USUARIO, ADMIN

## Ejemplos con curl

Listar todos los usuarios:
```bash
curl -X GET http://localhost:8080/api/usuarios/todos \
  -H "Authorization: Bearer [tu_token]"
```

Buscar usuario por nombre:
```bash
curl -X GET "http://localhost:8080/api/usuarios/nombre?nombre=Juan" \
  -H "Authorization: Bearer [tu_token]"
```

Crear nuevo usuario:
```bash
curl -X POST "http://localhost:8080/api/usuarios/crear?correo=test@example.com" \
  -H "Authorization: Bearer [tu_token]" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan",
    "apellido": "Pérez",
    "nombreUsuario": "juanp",
    "correo": "test@example.com",
    "clave": "MiPassword123@",
    "roles": "USUARIO"
  }'
```

Listar todos los productos:
```bash
curl -X GET http://localhost:8080/api/consulta/productos \
  -H "Authorization: Bearer [tu_token]"
```

Buscar productos por categoría:
```bash
curl -X GET "http://localhost:8080/api/consulta/productos/categoria?categoria=ELECTRONICA" \
  -H "Authorization: Bearer [tu_token]"
```

## URLs para pruebas

### Autenticación
- Login: `POST http://localhost:8080/api/auth/login`

### Usuarios (Solo ADMIN)
- Listar todos: `GET http://localhost:8080/api/usuarios/todos`
- Por ID: `GET http://localhost:8080/api/usuarios/1`
- Por nombre: `GET http://localhost:8080/api/usuarios/nombre?nombre=Juan`
- Por apellido: `GET http://localhost:8080/api/usuarios/apellido?apellido=Pérez`
- Por nombre usuario: `GET http://localhost:8080/api/usuarios/nombre-usuario?nombreUsuario=juanp`
- Por correo: `GET http://localhost:8080/api/usuarios/correo?correo=test@example.com`
- Crear: `POST http://localhost:8080/api/usuarios/crear?correo=test@example.com`
- Actualizar: `PUT http://localhost:8080/api/usuarios/actualizar/1`
- Cambiar clave: `PUT http://localhost:8080/api/usuarios/cambiar-clave/1`
- Desactivar por ID: `PUT http://localhost:8080/api/usuarios/desactivar/id/1`
- Desactivar por nombre: `PUT http://localhost:8080/api/usuarios/desactivar/nombre-usuario?nombreUsuario=juanp`
- Desactivar por correo: `PUT http://localhost:8080/api/usuarios/desactivar/correo?correo=test@example.com`

### Productos (ADMIN/USUARIO)
- Por ID: `GET http://localhost:8080/api/productos/1`
- Por nombre: `GET http://localhost:8080/api/productos/nombre?nombre=iPhone`
- Por estado: `GET http://localhost:8080/api/productos/estado?estado=ACTIVO`
- Por categoría: `GET http://localhost:8080/api/productos/categoria?categoria=ELECTRONICA`
- Por rango precio: `GET http://localhost:8080/api/productos/precio?min=100&max=500`
- Crear: `POST http://localhost:8080/api/productos/crear`
- Actualizar: `PUT http://localhost:8080/api/productos/actualizar/1`
- Desactivar: `PUT http://localhost:8080/api/productos/desactivar/1`

### Categorías (ADMIN/USUARIO)
- Por ID: `GET http://localhost:8080/api/categorias/1`
- Buscar: `GET http://localhost:8080/api/categorias/buscar?nombre=Smartphones&categoria=ELECTRONICA`
- Por categoría: `GET http://localhost:8080/api/categorias/categorias?categoria=ELECTRONICA`
- Por estado: `GET http://localhost:8080/api/categorias/estado?estado=ACTIVO`
- Crear: `POST http://localhost:8080/api/categorias/crear`
- Actualizar: `PUT http://localhost:8080/api/categorias/actualizar/1`
- Desactivar: `PUT http://localhost:8080/api/categorias/desactivar/1`

### Consultas (ADMIN/USUARIO)
- Todos los productos: `GET http://localhost:8080/api/consulta/productos`
- Producto por nombre: `GET http://localhost:8080/api/consulta/productos/nombre?nombre=iPhone`
- Producto por categoría: `GET http://localhost:8080/api/consulta/productos/categoria?categoria=ELECTRONICA`
- Producto por estado: `GET http://localhost:8080/api/consulta/productos/estado?estado=ACTIVO`
- Producto por precio: `GET http://localhost:8080/api/consulta/productos/precio?min=100&max=500`
- Todas las categorías: `GET http://localhost:8080/api/consulta/categorias`
- Categoría por nombre: `GET http://localhost:8080/api/consulta/categorias/nombre?categorias=ELECTRONICA`
- Categoría por estado: `GET http://localhost:8080/api/consulta/categorias/estado?estado=ACTIVO`

## Problemas comunes

### Error: "Usuario ya existe"
El sistema no permite correos duplicados. Verifica que no estés creando usuarios con el mismo correo.

### Error: "Token inválido"
El token expiró (10 minutos). Haz login nuevamente.

### Error: "Acceso denegado"
Verifica que estés usando el header `Authorization: Bearer [token]` y que tu usuario tenga los permisos necesarios.

### Resetear Base de Datos
Si necesitas empezar desde cero:
```sql
DELETE FROM usuarios;
DELETE FROM productos;
DELETE FROM categoria_productos;
DBCC CHECKIDENT ('usuarios', RESEED, 0);
DBCC CHECKIDENT ('productos', RESEED, 0);
DBCC CHECKIDENT ('categoria_productos', RESEED, 0);
```

## Roles y Permisos
Para iniciar sesion: `/api/auth/login`
Para consumir las apis de UsuarioController`/api/usuarios/**`
Para consumir las apis de Productos`/api/productos/**` 
Para consumir las apis de CategoriasProductos`/api/categorias/**` 

## Validaciones

### Contraseñas
- Mínimo 8 caracteres, máximo 20
- Debe contener: letras, números y al menos un símbolo especial
- Ejemplo válido: `MiPassword123@`

### Correos
- Formato de email válido
- Único en el sistema

## Notas importantes

1. No hagas múltiples "Send" en Postman sin verificar la respuesta
2. El admin se crea automáticamente al iniciar la aplicación
3. Los tokens expiran en 10 minutos
4. Usa siempre el header Authorization para endpoints protegidos
5. La base de datos tiene restricciones UNIQUE - no se pueden duplicar correos

## Soporte

Si encuentras problemas:
1. Verifica los logs de la aplicación
2. Confirma que la base de datos esté conectada
3. Revisa que el token no haya expirado
4. Valida que los datos cumplan con las restricciones
