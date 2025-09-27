package com.gestion.GestionDeProductos.dto;

import com.gestion.GestionDeProductos.model.Estado;
import lombok.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActualizarCategoriaDTO {
    
    @Size(max = 100)
    private String nombre;
    
    private Estado estado;
}
