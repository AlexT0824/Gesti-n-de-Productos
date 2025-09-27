package com.gestion.GestionDeProductos.controller;

import com.gestion.GestionDeProductos.model.Usuarios;
import com.gestion.GestionDeProductos.service.UsuariosService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class InicioSesionController {

    private final UsuariosService usuariosService;
    private static final String JWT_SECRET = "MiClaveSecretaSuperSegura";

    public InicioSesionController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> iniciarSesion(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String clave = loginData.get("clave");

        Usuarios usuario = usuariosService.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!new BCryptPasswordEncoder().matches(clave, usuario.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = Jwts.builder()
                .setSubject(usuario.getCorreo())
                .claim("roles", usuario.getRoles().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
