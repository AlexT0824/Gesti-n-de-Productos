package com.gestion.GestionDeProductos;

import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.model.Roles;
import com.gestion.GestionDeProductos.model.Usuarios;
import com.gestion.GestionDeProductos.repository.UsuariosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GestionDeProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDeProductosApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UsuariosRepository usuariosRepository, PasswordEncoder encoder) {
		return args -> {
			try {
				if (!usuariosRepository.existsByCorreo("admin@tuapp.com")) {
					Usuarios admin = new Usuarios();
					admin.setNombre("Super");
					admin.setApellido("Admin");
					admin.setNombreUsuario("admin");
					admin.setCorreo("admin@tuapp.com");
					admin.setClave(encoder.encode("Admin123@"));
					admin.setRoles(Roles.ADMIN);
					admin.setEstado(Estado.ACTIVO);
					admin.setBloqueado(false);
					admin.setIntentosLogin(0);
					usuariosRepository.save(admin);
					System.out.println("--Admin creado por defecto");
					System.out.println("--Correo: admin@tuapp.com");
					System.out.println("--Contraseña: Admin123@");
				} else {
					System.out.println("--Admin ya existe en la base de datos");
				}
			} catch (Exception e) {
				System.err.println("--Error al crear admin: " + e.getMessage());
			}
		};
	}
}
