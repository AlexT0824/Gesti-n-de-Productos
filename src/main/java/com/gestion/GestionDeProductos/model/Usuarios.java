package com.gestion.GestionDeProductos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    private String apellido;

    @Column(nullable = false, length = 30)
    @NotBlank
    @Size(min = 3, max = 30)
    private String nombreUsuario;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String correo;

    @Column(nullable = false, length = 255)
    @NotBlank
    private String clave;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacionUser;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime fechaUltimoIngreso;

    @Column(nullable = false)
    private int intentosLogin = 0;

    @Column(nullable = false)
    private boolean bloqueado = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles roles;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;
}
