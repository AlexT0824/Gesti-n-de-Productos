# 🏪 Sistema de Gestión de Productos

API REST para la gestión de productos, categorías y usuarios con autenticación JWT.

## 🚀 Inicio Rápido

### Prerrequisitos
- Java 8 o superior
- Maven 3.6+
- SQL Server (o H2 para desarrollo)

### Instalación
1. Clona el repositorio
2. Configura la base de datos en `application.properties`
3. Ejecuta: `mvn spring-boot:run`

## 🔐 Autenticación y Autorización

### Paso 1: Crear Admin Automático
Al ejecutar la aplicación, se crea automáticamente un usuario administrador:

```
📧 Correo: admin@tuapp.com
🔑 Contraseña: Admin123@
```

### Paso 2: Iniciar Sesión
**Endpoint:** `POST /api/auth/login`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
    "correo": "admin@tuapp.com",
    "clave": "Admin123@"
}
```

**Respuesta:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Paso 3: Usar el Token
Para todas las demás peticiones, incluye el token en el header:

```
Authorization: Bearer [tu_token_aqui]
```

**⚠️ Importante:** El token expira en 10 minutos. Si expira, debes hacer login nuevamente.

## 📋 API Endpoints Detallados

### 🔑 Autenticación
| Método | URL | Headers | Body | Descripción |
|--------|-----|---------|------|-------------|
| POST | `/api/auth/login` | `Content-Type: application/json` | `{"correo": "admin@tuapp.com", "clave": "Admin123@"}` | Iniciar sesión |

### 👥 Usuarios (Solo ADMIN)
| Método | URL | Headers | Parámetros | Body | Descripción |
|--------|-----|---------|------------|------|-------------|
| GET | `/api/usuarios/todos` | `Authorization: Bearer [token]` | - | - | Listar todos los usuarios |
| GET | `/api/usuarios/{id}` | `Authorization: Bearer [token]` | `id` (path) | - | Obtener usuario por ID |
| GET | `/api/usuarios/nombre` | `Authorization: Bearer [token]` | `nombre` (query) | - | Buscar por nombre |
| GET | `/api/usuarios/apellido` | `Authorization: Bearer [token]` | `apellido` (query) | - | Buscar por apellido |
| GET | `/api/usuarios/nombre-usuario` | `Authorization: Bearer [token]` | `nombreUsuario` (query) | - | Buscar por nombre de usuario |
| GET | `/api/usuarios/correo` | `Authorization: Bearer [token]` | `correo` (query) | - | Buscar por correo |
| GET | `/api/usuarios/rango-fecha-creacion` | `Authorization: Bearer [token]` | `inicio` y `fin` (query) | - | Buscar por rango de fecha |
| GET | `/api/usuarios/rango-ultimo-ingreso` | `Authorization: Bearer [token]` | `fechaUltimoIngreso` (query) | - | Buscar por último ingreso |
| POST | `/api/usuarios/crear` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | `correo` (query) | Ver abajo | Crear nuevo usuario |
| PUT | `/api/usuarios/actualizar/{id}` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | `id` (path) | Ver abajo | Actualizar usuario |
| PUT | `/api/usuarios/cambiar-clave/{id}` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | `id` (path) | `{"clave": "NuevaClave123@"}` | Cambiar contraseña |
| PUT | `/api/usuarios/desactivar/id/{id}` | `Authorization: Bearer [token]` | `id` (path) | - | Desactivar usuario por ID |
| PUT | `/api/usuarios/desactivar/nombre-usuario` | `Authorization: Bearer [token]` | `nombreUsuario` (query) | - | Desactivar por nombre usuario |
| PUT | `/api/usuarios/desactivar/correo` | `Authorization: Bearer [token]` | `correo` (query) | - | Desactivar por correo |

### 📦 Productos (ADMIN/USUARIO)
| Método | URL | Headers | Parámetros | Body | Descripción |
|--------|-----|---------|------------|------|-------------|
| GET | `/api/productos/{id}` | `Authorization: Bearer [token]` | `id` (path) | - | Obtener producto por ID |
| GET | `/api/productos/nombre` | `Authorization: Bearer [token]` | `nombre` (query) | - | Buscar por nombre |
| GET | `/api/productos/estado` | `Authorization: Bearer [token]` | `estado` (query) | - | Buscar por estado |
| GET | `/api/productos/rango-fecha-creacion` | `Authorization: Bearer [token]` | `inicio` y `fin` (query) | - | Buscar por rango de fecha |
| GET | `/api/productos/rango-fecha-actualizacion` | `Authorization: Bearer [token]` | `fechaUltimaActualizacion` (query) | - | Buscar por fecha actualización |
| GET | `/api/productos/categoria` | `Authorization: Bearer [token]` | `categoria` (query) | - | Buscar por categoría |
| GET | `/api/productos/precio` | `Authorization: Bearer [token]` | `min` y `max` (query) | - | Buscar por rango de precio |
| POST | `/api/productos/crear` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | - | Ver abajo | Crear producto |
| PUT | `/api/productos/actualizar/{id}` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | `id` (path) | Ver abajo | Actualizar producto |
| PUT | `/api/productos/desactivar/{id}` | `Authorization: Bearer [token]` | `id` (path) | - | Desactivar producto |

### 🏷️ Categorías (ADMIN/USUARIO)
| Método | URL | Headers | Parámetros | Body | Descripción |
|--------|-----|---------|------------|------|-------------|
| GET | `/api/categorias/{id}` | `Authorization: Bearer [token]` | `id` (path) | - | Obtener categoría por ID |
| GET | `/api/categorias/buscar` | `Authorization: Bearer [token]` | `nombre` y `categoria` (query) | - | Buscar por nombre y categoría |
| GET | `/api/categorias/categorias` | `Authorization: Bearer [token]` | `categoria` (query) | - | Buscar por categoría |
| GET | `/api/categorias/estado` | `Authorization: Bearer [token]` | `estado` (query) | - | Buscar por estado |
| POST | `/api/categorias/crear` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | - | Ver abajo | Crear categoría |
| PUT | `/api/categorias/actualizar/{id}` | `Authorization: Bearer [token]`<br>`Content-Type: application/json` | `id` (path) | Ver abajo | Actualizar categoría |
| PUT | `/api/categorias/desactivar/{id}` | `Authorization: Bearer [token]` | `id` (path) | - | Desactivar categoría |

### 📊 Consultas (ADMIN/USUARIO)
| Método | URL | Headers | Parámetros | Body | Descripción |
|--------|-----|---------|------------|------|-------------|
| GET | `/api/consulta/productos` | `Authorization: Bearer [token]` | - | - | Listar TODOS los productos |
| GET | `/api/consulta/productos/nombre` | `Authorization: Bearer [token]` | `nombre` (query) | - | Buscar producto por nombre |
| GET | `/api/consulta/productos/categoria` | `Authorization: Bearer [token]` | `categoria` (query) | - | Buscar producto por categoría |
| GET | `/api/consulta/productos/estado` | `Authorization: Bearer [token]` | `estado` (query) | - | Buscar producto por estado |
| GET | `/api/consulta/productos/precio` | `Authorization: Bearer [token]` | `min` y `max` (query) | - | Buscar producto por rango precio |
| GET | `/api/consulta/categorias` | `Authorization: Bearer [token]` | - | - | Listar TODAS las categorías |
| GET | `/api/consulta/categorias/nombre` | `Authorization: Bearer [token]` | `categorias` (query) | - | Buscar categoría por nombre |
| GET | `/api/consulta/categorias/estado` | `Authorization: Bearer [token]` | `estado` (query) | - | Buscar categoría por estado |

## 🔧 Configuración de Postman

### 1. Crear Variables de Entorno
En Postman, crea un Environment con:
- `base_url`: `http://localhost:8080`
- `token`: (se llenará automáticamente)

### 2. Configurar Login
**Request:** `POST {{base_url}}/api/auth/login`

**Tests Script:**
```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("token", response.token);
}
```

### 3. Configurar Autorización Global
En cada request que requiera autenticación:
**Authorization Tab:** 
- Type: `Bearer Token`
- Token: `{{token}}`

## 📝 Ejemplos de Bodies para POST/PUT

### 👥 Crear Usuario
**POST** `/api/usuarios/crear?correo=test@example.com`
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

### 👥 Actualizar Usuario
**PUT** `/api/usuarios/actualizar/1`
```json
{
    "nombre": "Juan Carlos",
    "apellido": "Pérez García",
    "nombreUsuario": "juancarlos",
    "correo": "juan.nuevo@example.com",
    "roles": "ADMIN"
}
```

### 📦 Crear Producto
**POST** `/api/productos/crear`
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

### 📦 Actualizar Producto
**PUT** `/api/productos/actualizar/1`
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

### 🏷️ Crear Categoría
**POST** `/api/categorias/crear`
```json
{
    "categorias": "ELECTRONICA",
    "nombre": "Smartphones Premium",
    "estado": "ACTIVO"
}
```

### 🏷️ Actualizar Categoría
**PUT** `/api/categorias/actualizar/1`
```json
{
    "categorias": "ELECTRONICA",
    "nombre": "Dispositivos Móviles Premium",
    "estado": "ACTIVO"
}
```

## 📋 Valores Válidos para Enums

### Categorias
```
ELECTRONICA, ROPA, ALIMENTACION, HOGAR, JUGUETES, DEPORTES, LIBROS, OTROS
```

### Estados
```
ACTIVO, DESACTIVO, BORRADO
```

### Roles
```
USUARIO, ADMIN
```

## 📝 Ejemplos de Uso Completos

### 1. Listar Todos los Usuarios
```bash
curl -X GET http://localhost:8080/api/usuarios/todos \
  -H "Authorization: Bearer [tu_token]"
```

### 2. Buscar Usuario por Nombre
```bash
curl -X GET "http://localhost:8080/api/usuarios/nombre?nombre=Juan" \
  -H "Authorization: Bearer [tu_token]"
```

### 3. Crear Nuevo Usuario
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

### 4. Listar Todos los Productos
```bash
curl -X GET http://localhost:8080/api/consulta/productos \
  -H "Authorization: Bearer [tu_token]"
```

### 5. Buscar Productos por Categoría
```bash
curl -X GET "http://localhost:8080/api/consulta/productos/categoria?categoria=ELECTRONICA" \
  -H "Authorization: Bearer [tu_token]"
```

### 6. Buscar Productos por Rango de Precio
```bash
curl -X GET "http://localhost:8080/api/consulta/productos/precio?min=100&max=500" \
  -H "Authorization: Bearer [token]"
```

### 7. Listar Todas las Categorías
```bash
curl -X GET http://localhost:8080/api/consulta/categorias \
  -H "Authorization: Bearer [tu_token]"
```

## 🔗 URLs Completas para Pruebas Rápidas

### 🔑 Autenticación
- **Login:** `POST http://localhost:8080/api/auth/login`

### 👥 Usuarios (Solo ADMIN)
- **Listar todos:** `GET http://localhost:8080/api/usuarios/todos`
- **Por ID:** `GET http://localhost:8080/api/usuarios/1`
- **Por nombre:** `GET http://localhost:8080/api/usuarios/nombre?nombre=Juan`
- **Por apellido:** `GET http://localhost:8080/api/usuarios/apellido?apellido=Pérez`
- **Por nombre usuario:** `GET http://localhost:8080/api/usuarios/nombre-usuario?nombreUsuario=juanp`
- **Por correo:** `GET http://localhost:8080/api/usuarios/correo?correo=test@example.com`
- **Crear:** `POST http://localhost:8080/api/usuarios/crear?correo=test@example.com`
- **Actualizar:** `PUT http://localhost:8080/api/usuarios/actualizar/1`
- **Cambiar clave:** `PUT http://localhost:8080/api/usuarios/cambiar-clave/1`
- **Desactivar por ID:** `PUT http://localhost:8080/api/usuarios/desactivar/id/1`
- **Desactivar por nombre:** `PUT http://localhost:8080/api/usuarios/desactivar/nombre-usuario?nombreUsuario=juanp`
- **Desactivar por correo:** `PUT http://localhost:8080/api/usuarios/desactivar/correo?correo=test@example.com`

### 📦 Productos (ADMIN/USUARIO)
- **Por ID:** `GET http://localhost:8080/api/productos/1`
- **Por nombre:** `GET http://localhost:8080/api/productos/nombre?nombre=iPhone`
- **Por estado:** `GET http://localhost:8080/api/productos/estado?estado=ACTIVO`
- **Por categoría:** `GET http://localhost:8080/api/productos/categoria?categoria=ELECTRONICA`
- **Por rango precio:** `GET http://localhost:8080/api/productos/precio?min=100&max=500`
- **Crear:** `POST http://localhost:8080/api/productos/crear`
- **Actualizar:** `PUT http://localhost:8080/api/productos/actualizar/1`
- **Desactivar:** `PUT http://localhost:8080/api/productos/desactivar/1`

### 🏷️ Categorías (ADMIN/USUARIO)
- **Por ID:** `GET http://localhost:8080/api/categorias/1`
- **Buscar:** `GET http://localhost:8080/api/categorias/buscar?nombre=Smartphones&categoria=ELECTRONICA`
- **Por categoría:** `GET http://localhost:8080/api/categorias/categorias?categoria=ELECTRONICA`
- **Por estado:** `GET http://localhost:8080/api/categorias/estado?estado=ACTIVO`
- **Crear:** `POST http://localhost:8080/api/categorias/crear`
- **Actualizar:** `PUT http://localhost:8080/api/categorias/actualizar/1`
- **Desactivar:** `PUT http://localhost:8080/api/categorias/desactivar/1`

### 📊 Consultas (ADMIN/USUARIO)
- **Todos los productos:** `GET http://localhost:8080/api/consulta/productos`
- **Producto por nombre:** `GET http://localhost:8080/api/consulta/productos/nombre?nombre=iPhone`
- **Producto por categoría:** `GET http://localhost:8080/api/consulta/productos/categoria?categoria=ELECTRONICA`
- **Producto por estado:** `GET http://localhost:8080/api/consulta/productos/estado?estado=ACTIVO`
- **Producto por precio:** `GET http://localhost:8080/api/consulta/productos/precio?min=100&max=500`
- **Todas las categorías:** `GET http://localhost:8080/api/consulta/categorias`
- **Categoría por nombre:** `GET http://localhost:8080/api/consulta/categorias/nombre?categorias=ELECTRONICA`
- **Categoría por estado:** `GET http://localhost:8080/api/consulta/categorias/estado?estado=ACTIVO`

## 🛠️ Resolución de Problemas

### Error: "Usuario ya existe"
- El sistema valida que no haya correos duplicados
- Si ocurre, verifica que no estés creando usuarios con el mismo correo

### Error: "Token inválido"
- El token expiró (10 minutos)
- Haz login nuevamente para obtener un nuevo token

### Error: "Acceso denegado"
- Verifica que estés usando el header `Authorization: Bearer [token]`
- Confirma que tu usuario tenga los permisos necesarios

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

## 🔒 Roles y Permisos

| Endpoint | ADMIN | USUARIO |
|----------|-------|---------|
| `/api/auth/login` | ✅ | ✅ |
| `/api/usuarios/**` | ✅ | ❌ |
| `/api/productos/**` | ✅ | ✅ |
| `/api/categorias/**` | ✅ | ✅ |

## 📋 Validaciones

### Contraseñas
- Mínimo 8 caracteres, máximo 20
- Debe contener: letras, números y al menos un símbolo especial
- Ejemplo válido: `MiPassword123@`

### Correos
- Formato de email válido
- Único en el sistema

## 🚨 Notas Importantes

1. **No hagas múltiples "Send"** en Postman sin verificar la respuesta
2. **El admin se crea automáticamente** al iniciar la aplicación
3. **Los tokens expiran en 10 minutos** - planifica tus pruebas
4. **Usa siempre el header Authorization** para endpoints protegidos
5. **La base de datos tiene restricciones UNIQUE** - no se pueden duplicar correos

## 📞 Soporte

Si encuentras problemas:
1. Verifica los logs de la aplicación
2. Confirma que la base de datos esté conectada
3. Revisa que el token no haya expirado
4. Valida que los datos cumplan con las restricciones
