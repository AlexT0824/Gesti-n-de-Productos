package com.gestion.GestionDeProductos.repository;

import com.gestion.GestionDeProductos.model.CategoriaProductos;
import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaProductosRepository extends JpaRepository<CategoriaProductos, Long> {

    //Leer
    List<CategoriaProductos> findByCategorias(Categorias categorias);
    List<CategoriaProductos> findByNombreAndCategorias(String nombre, Categorias categorias);
    List<CategoriaProductos> findByEstado(Estado estado);


}


