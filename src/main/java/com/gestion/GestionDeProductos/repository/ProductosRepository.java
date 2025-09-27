package com.gestion.GestionDeProductos.repository;

import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ProductosRepository extends JpaRepository<Productos, Long> {

    //Leer
    List<Productos> findByNombre(String nombre);
    List<Productos> findByEstado(Estado estado);
    List<Productos> findByFechaCreacionBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Productos> findByFechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion);
    List<Productos> findByCategorias(Categorias categorias);
    List<Productos> findByPrecioBetween(Double min, Double max);
}

