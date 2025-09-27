package com.gestion.GestionDeProductos.controller;

import com.gestion.GestionDeProductos.dto.ActualizarUsuarioDTO;
import com.gestion.GestionDeProductos.model.Usuarios;
import com.gestion.GestionDeProductos.service.UsuariosService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private final UsuariosService usuariosService;

    public UsuariosController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Usuarios> crearUsuario(@RequestBody Usuarios usuario,
                                                 @RequestParam String correo) {
        Usuarios creado = usuariosService.crearUsuario(usuario, correo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Usuarios>> listarUsuarios() {
        List<Usuarios> usuarios = usuariosService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuarios>> obtenerPorId(@PathVariable Long id) {
        Optional<Usuarios> usuario = usuariosService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<Usuarios>> buscarPorNombre(@RequestParam String nombre) {
        List<Usuarios> usuarios = usuariosService.findByNombre(nombre);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/apellido")
    public ResponseEntity<List<Usuarios>> buscarPorApellido(@RequestParam String apellido) {
        List<Usuarios> usuarios = usuariosService.findByApellido(apellido);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/nombre-usuario")
    public ResponseEntity<Optional<Usuarios>> buscarPorNombreUsuario(@RequestParam String nombreUsuario) {
        Optional<Usuarios> usuario = usuariosService.findByNombreUsuario(nombreUsuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/correo")
    public ResponseEntity<Optional<Usuarios>> buscarPorCorreo(@RequestParam String correo) {
        Optional<Usuarios> usuario = usuariosService.findByCorreo(correo);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/rango-fecha-creacion")
    public ResponseEntity<List<Usuarios>> buscarPorRangoFechaCreacion(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime fin) {
        List<Usuarios> usuarios = usuariosService.findByRangoFechaUsuarios(inicio, fin);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/rango-ultimo-ingreso")
    public ResponseEntity<List<Usuarios>> buscarPorRangoUltimosIngresos(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime fechaUltimoIngreso) {
        List<Usuarios> usuarios = usuariosService.findByRangoUltimosIngresos(fechaUltimoIngreso);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuarios> actualizarUsuario(@RequestBody ActualizarUsuarioDTO usuario,
                                                      @PathVariable Long id) {
        Usuarios actualizado = usuariosService.actualizarUsuario(usuario, id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/cambiar-clave/{id}")
    public ResponseEntity<Usuarios> actualizarContraseña(@RequestBody Usuarios usuario,
                                                         @PathVariable Long id) {
        Usuarios actualizado = usuariosService.actualizarContraseña(usuario, id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/desactivar/id/{id}")
    public ResponseEntity<Void> desactivarPorId(@PathVariable Long id) {
        usuariosService.desactivarId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/desactivar/nombre-usuario")
    public ResponseEntity<Void> desactivarPorNombreUsuario(@RequestParam String nombreUsuario) {
        usuariosService.desactivarNombreUsuario(nombreUsuario);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/desactivar/correo")
    public ResponseEntity<Void> desactivarPorCorreo(@RequestParam String correo) {
        usuariosService.desactivarCorreo(correo);
        return ResponseEntity.noContent().build();
    }
}