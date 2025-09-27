package com.gestion.GestionDeProductos.dto;

import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.model.Roles;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class ActualizarUsuarioDTO {
    
    @Size(max = 100)
    private String nombre;
    
    @Size(max = 100)
    private String apellido;
    
    @Size(min = 3, max = 30)
    private String nombreUsuario;
    
    @Email
    private String correo;
    
    private Roles roles;
    
    private Estado estado;
}
