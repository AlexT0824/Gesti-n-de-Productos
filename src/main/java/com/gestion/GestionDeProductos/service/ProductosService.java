package com.gestion.GestionDeProductos.service;

import com.gestion.GestionDeProductos.dto.ActualizarProductoDTO;
import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.model.Productos;
import com.gestion.GestionDeProductos.repository.ProductosRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {

    private final ProductosRepository productosRepository;

    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    @Transactional
    public Productos crearProducto(Productos producto) {
        producto.setEstado(Estado.ACTIVO);
        return productosRepository.save(producto);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public Optional<Productos> findById(Long id) {
        return productosRepository.findById(id);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<Productos> findByNombre(String nombre) {
        return productosRepository.findByNombre(nombre);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<Productos> findByEstado(Estado estado) {
        return productosRepository.findByEstado(estado);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<Productos> findByRangoFechaCreacionProducto(LocalDateTime inicio, LocalDateTime fin) {
        return productosRepository.findByFechaCreacionBetween(inicio, fin);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<Productos> findByRangoFechaActualizacion(LocalDateTime fechaUltimaActualizacion) {
        return productosRepository.findByFechaUltimaActualizacion(fechaUltimaActualizacion);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<Productos> findByCategoria(Categorias categorias) {
        return productosRepository.findByCategorias(categorias);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    public List<Productos> findByPrecioEntre(double min, double max) {
        return productosRepository.findByPrecioBetween(min, max);
    }

    @PreAuthorize("hasAnyRole('USUARIO','ADMIN')")
    @Transactional
    public Productos actualizarProducto(ActualizarProductoDTO productoActualizado, Long id) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (productoActualizado.getNombre() != null
                && !productoActualizado.getNombre().trim().isEmpty()
                && !productoActualizado.getNombre().equals(producto.getNombre())) {
            producto.setNombre(productoActualizado.getNombre());
        }

        if (productoActualizado.getCategorias() != null
                && !productoActualizado.getCategorias().equals(producto.getCategorias())) {
            producto.setCategorias(productoActualizado.getCategorias());
        }

        if (productoActualizado.getCosto() != null
                && !productoActualizado.getCosto().equals(producto.getCosto())) {
            producto.setCosto(productoActualizado.getCosto());
        }

        if (productoActualizado.getPrecio() != null
                && !productoActualizado.getPrecio().equals(producto.getPrecio())) {
            producto.setPrecio(productoActualizado.getPrecio());
        }

        if (productoActualizado.getListaTags() != null
                && !productoActualizado.getListaTags().trim().isEmpty()
                && !productoActualizado.getListaTags().equals(producto.getListaTags())) {
            producto.setListaTags(productoActualizado.getListaTags());
        }

        if (productoActualizado.getEstado() != null
                && !productoActualizado.getEstado().equals(producto.getEstado())) {
            producto.setEstado(productoActualizado.getEstado());
        }

        return productosRepository.save(producto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void desactivarProducto(Long id) {
        Productos producto = productosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setEstado(Estado.BORRADO);
        productosRepository.save(producto);
    }
}
