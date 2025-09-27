package com.gestion.GestionDeProductos.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
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
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categorias categorias;

    @Column(nullable = false)
    private double costo;

    @Column(nullable = false)
    private double precio;

    @Column(length = 255)
    private String listaTags;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime fechaUltimaActualizacion;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado;
}
