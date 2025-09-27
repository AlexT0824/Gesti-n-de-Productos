package com.gestion.GestionDeProductos.dto;

import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Estado;
import lombok.*;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarProductoDTO {
    
    @Size(max = 100)
    private String nombre;
    
    private Categorias categorias;
    
    private Double costo;
    
    private Double precio;
    
    @Size(max = 255)
    private String listaTags;
    
    private Estado estado;
}
