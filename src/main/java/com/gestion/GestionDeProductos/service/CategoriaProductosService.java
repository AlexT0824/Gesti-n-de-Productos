package com.gestion.GestionDeProductos.service;

import com.gestion.GestionDeProductos.dto.ActualizarCategoriaDTO;
import com.gestion.GestionDeProductos.model.CategoriaProductos;
import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.repository.CategoriaProductosRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProductosService {

    private final CategoriaProductosRepository categoriaProductosRepository;

    public CategoriaProductosService(CategoriaProductosRepository categoriaProductosRepository) {
        this.categoriaProductosRepository = categoriaProductosRepository;
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    @Transactional
    public CategoriaProductos crearCategoria(CategoriaProductos categoria) {
        categoria.setEstado(Estado.ACTIVO);
        return categoriaProductosRepository.save(categoria);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<CategoriaProductos> listarTodasCategorias() {
        return categoriaProductosRepository.findAll();
    }


    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public Optional<CategoriaProductos> findById(Long id) {
        return categoriaProductosRepository.findById(id);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<CategoriaProductos> findByNombreAndCategoria(String nombre, Categorias categorias) {
        return categoriaProductosRepository.findByNombreAndCategorias(nombre, categorias);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<CategoriaProductos> findByEstado(Estado estado) {
        return categoriaProductosRepository.findByEstado(estado);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    @Transactional
    public CategoriaProductos actualizarCategoria(ActualizarCategoriaDTO categoriaActualizada, Long id) {
        CategoriaProductos categoria = categoriaProductosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (categoriaActualizada.getNombre() != null
                && !categoriaActualizada.getNombre().trim().isEmpty()
                && !categoriaActualizada.getNombre().equals(categoria.getNombre())) {
            categoria.setNombre(categoriaActualizada.getNombre());
        }

        if (categoriaActualizada.getEstado() != null
                && !categoriaActualizada.getEstado().equals(categoria.getEstado())) {
            categoria.setEstado(categoriaActualizada.getEstado());
        }

        categoria.setFechaUltimaActualizacion(LocalDateTime.now());

        return categoriaProductosRepository.save(categoria);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void desactivarCategoria(Long id) {
        CategoriaProductos categoria = categoriaProductosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        categoria.setEstado(Estado.BORRADO);
        categoria.setFechaUltimaActualizacion(LocalDateTime.now());

        categoriaProductosRepository.save(categoria);
    }

}


