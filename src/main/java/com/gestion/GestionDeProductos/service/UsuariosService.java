package com.gestion.GestionDeProductos.service;

import com.gestion.GestionDeProductos.dto.ActualizarUsuarioDTO;
import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.model.Roles;
import com.gestion.GestionDeProductos.model.Usuarios;
import com.gestion.GestionDeProductos.repository.UsuariosRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {
    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuariosService(UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Crear !!ADMINS¡¡
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Usuarios crearUsuario(Usuarios usuarios, String correo) {
        if(usuariosRepository.existsByCorreo(usuarios.getCorreo())){
            throw new RuntimeException("Usuario ya existe");
        }

        String claveOriginal = usuarios.getClave();
        if (!validarContraseña(claveOriginal)) {
            throw new RuntimeException("La contraseña debe tener letras, números y debe contar con al menos símbolo");
        }
        
        usuarios.setClave(passwordEncoder.encode(claveOriginal));
        usuarios.setEstado(Estado.ACTIVO);
        usuarios.setFechaCreacionUser(LocalDateTime.now());

        return usuariosRepository.save(usuarios);
    }
    
    private boolean validarContraseña(String contraseña) {
        if (contraseña == null || contraseña.length() < 8 || contraseña.length() > 20) {
            return false;
        }
        return contraseña.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,20}$");
    }

    //Leer ¡¡SOLO ADMINS!!
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Usuarios> findById(Long id){
        return usuariosRepository.findById(id);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuarios> findByNombre(String nombre){
        return usuariosRepository.findByNombre(nombre);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuarios> findByApellido(String apellido) {
        return usuariosRepository.findByApellido(apellido);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Usuarios> findByNombreUsuario(String nombreUsuario) {
        return usuariosRepository.findByNombreUsuario(nombreUsuario);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<Usuarios> findByCorreo(String correo) {
        return usuariosRepository.findByCorreo(correo);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuarios> findByRangoFechaUsuarios(LocalDateTime inicio, LocalDateTime fin) {
        return usuariosRepository.findByFechaCreacionUserBetween(inicio, fin);
    }
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuarios> findByRangoUltimosIngresos(LocalDateTime fechaUltimoIngreso) {
        return usuariosRepository.findByFechaUltimoIngreso(fechaUltimoIngreso);
    }

    //Actualizar ¡¡SOLO ADMINS!!
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
    @Transactional
    public Usuarios actualizarUsuario(ActualizarUsuarioDTO usuariosActualizado, Long id) {
        Usuarios usuarios = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuarioActual = auth.getName();
        
        Roles rolUsuario = usuarios.getRoles();
        String correoUsuario = usuarios.getCorreo();
        
        if (rolUsuario != Roles.ADMIN && !correoUsuario.equals(correoUsuarioActual)) {
            throw new RuntimeException("No tienes permisos para actualizar este usuario");
        }

        if (usuariosActualizado.getNombre() != null
                && !usuariosActualizado.getNombre().trim().isEmpty()
                && !usuariosActualizado.getNombre().equals(usuarios.getNombre())) {
            usuarios.setNombre(usuariosActualizado.getNombre());
        }

        if (usuariosActualizado.getApellido() != null
                && !usuariosActualizado.getApellido().trim().isEmpty()
                && !usuariosActualizado.getApellido().equals(usuarios.getApellido())) {
            usuarios.setApellido(usuariosActualizado.getApellido());
        }

        if (usuariosActualizado.getNombreUsuario() != null
                && !usuariosActualizado.getNombreUsuario().trim().isEmpty()
                && !usuariosActualizado.getNombreUsuario().equals(usuarios.getNombreUsuario())) {
            usuarios.setNombreUsuario(usuariosActualizado.getNombreUsuario());
        }

        if (usuariosActualizado.getCorreo() != null
                && !usuariosActualizado.getCorreo().trim().isEmpty()
                && !usuariosActualizado.getCorreo().equals(usuarios.getCorreo())) {
            usuarios.setCorreo(usuariosActualizado.getCorreo());
        }

        if (usuariosActualizado.getRoles() != null
                && !usuariosActualizado.getRoles().equals(usuarios.getRoles())) {
            usuarios.setRoles(usuariosActualizado.getRoles());
        }

        if (usuariosActualizado.getEstado() != null
                && !usuariosActualizado.getEstado().equals(usuarios.getEstado())) {
            usuarios.setEstado(usuariosActualizado.getEstado());
        }

        return usuariosRepository.save(usuarios);
    }

    @PreAuthorize("hasAnyRole('USUARIO', 'ADMIN')")
    @Transactional
    public Usuarios actualizarContraseña(Usuarios usuariosActualizado, Long id) {
        Usuarios usuarios = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoUsuarioActual = auth.getName();
        
        Roles rolUsuario = usuarios.getRoles();
        String correoUsuario = usuarios.getCorreo();
        
        if (rolUsuario != Roles.ADMIN && !correoUsuario.equals(correoUsuarioActual)) {
            throw new RuntimeException("No tienes permisos para actualizar la contraseña de este usuario");
        }

        usuarios.setClave(passwordEncoder.encode(usuariosActualizado.getClave()));
        return usuariosRepository.save(usuarios);
    }

    //DESACTIVAR ¡¡SOLO ADMINS!!
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void desactivarId(Long id) {
        Usuarios usuarios = usuariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuarios.setEstado(Estado.DESACTIVO);
        usuariosRepository.save(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void desactivarNombreUsuario(String nombreUsuario) {
        Usuarios usuarios = usuariosRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Nombre de usuario no encontrado."));

        usuarios.setEstado(Estado.DESACTIVO);
        usuariosRepository.save(usuarios);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void desactivarCorreo(String correo) {
        Usuarios usuarios = usuariosRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Correo no encontrado"));

        usuarios.setEstado(Estado.DESACTIVO);
        usuariosRepository.save(usuarios);
    }
}