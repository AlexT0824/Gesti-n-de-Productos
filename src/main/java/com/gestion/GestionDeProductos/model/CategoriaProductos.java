package com.gestion.GestionDeProductos.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProductos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Categorias categorias;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacionProducto;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime fechaUltimaActualizacion;
}

