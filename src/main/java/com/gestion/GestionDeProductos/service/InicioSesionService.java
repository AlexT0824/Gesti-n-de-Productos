package com.gestion.GestionDeProductos.service;

import com.gestion.GestionDeProductos.model.Usuarios;
import com.gestion.GestionDeProductos.repository.UsuariosRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class InicioSesionService {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int MAX_INTENTOS = 3;
    private static final String JWT_SECRET = "MiClaveSecretaSuperSegura";
    private static final long JWT_EXPIRATION = 1000 * 60 * 10; // 10 minutos

    public InicioSesionService(UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String iniciarSesion(String correo, String clave) {
        Usuarios usuario = usuariosRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.isBloqueado()) {
            return "Usuario bloqueado. Contacte al administrador.";
        }

        if (passwordEncoder.matches(clave, usuario.getClave())) {
            usuario.setFechaUltimoIngreso(LocalDateTime.now());
            usuario.setIntentosLogin(0);
            usuariosRepository.save(usuario);
            return generarToken(usuario);
        }

        usuario.setIntentosLogin(usuario.getIntentosLogin() + 1);
        if (usuario.getIntentosLogin() >= MAX_INTENTOS) {
            usuario.setBloqueado(true);
        }
        usuariosRepository.save(usuario);
        return "Contraseña incorrecta. Intento " + usuario.getIntentosLogin() + " de " + MAX_INTENTOS;
    }

    private String generarToken(Usuarios usuario) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("roles", usuario.getRoles().name())
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                .compact();
    }
}




