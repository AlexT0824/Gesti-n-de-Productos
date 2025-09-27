package com.gestion.GestionDeProductos.repository;

import com.gestion.GestionDeProductos.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {

    //Leer
    List<Usuarios> findByNombre(String nombre);
    List<Usuarios> findByApellido(String apellido);
    Optional<Usuarios> findByNombreUsuario(String nombreUsuario);
    List<Usuarios> findByFechaCreacionUserBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Usuarios> findByFechaUltimoIngreso(LocalDateTime fechaUltimoIngreso);
    Optional<Usuarios> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
