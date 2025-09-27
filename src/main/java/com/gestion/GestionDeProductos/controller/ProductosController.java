package com.gestion.GestionDeProductos.controller;

import com.gestion.GestionDeProductos.dto.ActualizarProductoDTO;
import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Productos;
import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.service.ProductosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.format.annotation.DateTimeFormat;

@RestController
@RequestMapping("/api/productos")
public class ProductosController {

    private final ProductosService productosService;

    public ProductosController(ProductosService productosService) {
        this.productosService = productosService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Productos> crearProducto(@Valid @RequestBody Productos producto) {
        Productos creado = productosService.crearProducto(producto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Productos>> obtenerPorId(@PathVariable Long id) {
        Optional<Productos> producto = productosService.findById(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/nombre")
    public ResponseEntity<List<Productos>> buscarPorNombre(@RequestParam String nombre) {
        List<Productos> productos = productosService.findByNombre(nombre);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<Productos>> buscarPorEstado(@RequestParam Estado estado) {
        List<Productos> productos = productosService.findByEstado(estado);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/rango-fecha-creacion")
    public ResponseEntity<List<Productos>> buscarPorRangoFechaCreacion(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime inicio,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime fin) {
        List<Productos> productos = productosService.findByRangoFechaCreacionProducto(inicio, fin);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/rango-fecha-actualizacion")
    public ResponseEntity<List<Productos>> buscarPorRangoFechaActualizacion(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime fechaUltimaActualizacion) {
        List<Productos> productos = productosService.findByRangoFechaActualizacion(fechaUltimaActualizacion);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Productos>> buscarPorCategoria(@RequestParam Categorias categoria) {
        List<Productos> productos = productosService.findByCategoria(categoria);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<Productos>> buscarPorRangoPrecio(
            @RequestParam double min,
            @RequestParam double max) {
        List<Productos> productos = productosService.findByPrecioEntre(min, max);
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Productos> actualizarProducto(@Valid @RequestBody ActualizarProductoDTO producto,
                                                        @PathVariable Long id) {
        Productos actualizado = productosService.actualizarProducto(producto, id);
        return ResponseEntity.ok(actualizado);
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) {
        productosService.desactivarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categorias-disponibles")
    public ResponseEntity<Categorias[]> obtenerCategoriasDisponibles() {
        Categorias[] categorias = Categorias.values();
        return ResponseEntity.ok(categorias);
    }
}
