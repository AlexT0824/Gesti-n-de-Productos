package com.gestion.GestionDeProductos.controller;

import com.gestion.GestionDeProductos.dto.ActualizarCategoriaDTO;
import com.gestion.GestionDeProductos.model.CategoriaProductos;
import com.gestion.GestionDeProductos.model.Categorias;
import com.gestion.GestionDeProductos.model.Estado;
import com.gestion.GestionDeProductos.service.CategoriaProductosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaProductosController {

    private final CategoriaProductosService categoriaProductosService;

    public CategoriaProductosController(CategoriaProductosService categoriaProductosService) {
        this.categoriaProductosService = categoriaProductosService;
    }

    @PostMapping("/crear")
    public ResponseEntity<CategoriaProductos> crearCategoria(@Valid @RequestBody CategoriaProductos categoria) {
        CategoriaProductos creada = categoriaProductosService.crearCategoria(categoria);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaProductos>> listarTodasCategorias() {
        return ResponseEntity.ok(categoriaProductosService.listarTodasCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoriaProductos>> obtenerPorId(@PathVariable Long id) {
        Optional<CategoriaProductos> categoria = categoriaProductosService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaProductos>> buscarPorNombreYCategoria(
            @RequestParam String nombre,
            @RequestParam Categorias categoria) {
        List<CategoriaProductos> categorias = categoriaProductosService.findByNombreAndCategoria(nombre, categoria);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/estado")
    public ResponseEntity<List<CategoriaProductos>> buscarPorEstado(@RequestParam Estado estado) {
        List<CategoriaProductos> categorias = categoriaProductosService.findByEstado(estado);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<CategoriaProductos> actualizarCategoria(
            @Valid @RequestBody ActualizarCategoriaDTO categoria,
            @PathVariable Long id) {
        CategoriaProductos actualizada = categoriaProductosService.actualizarCategoria(categoria, id);
        return ResponseEntity.ok(actualizada);
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Long id) {
        categoriaProductosService.desactivarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}

