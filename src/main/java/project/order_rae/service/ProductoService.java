// src/main/java/project/order_rae/service/ProductoService.java
package project.order_rae.service;

import project.order_rae.model.Producto;
import project.order_rae.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto insertar(Producto p) {
        if (p.getUsuario() == null || p.getCategoria() == null) {
            throw new RuntimeException("Usuario y Categoría son obligatorios");
        }
        return repo.save(p);
    }

    public Producto actualizar(Long id, Producto p) {
        Producto existente = obtenerPorId(id);

        existente.setCodigoProducto(p.getCodigoProducto());
        existente.setReferenciaProducto(p.getReferenciaProducto());
        existente.setColor(p.getColor());
        existente.setPrecio(p.getPrecio());
        existente.setEstadoProducto(p.getEstadoProducto());
        existente.setUsuario(p.getUsuario());
        existente.setCategoria(p.getCategoria());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Producto obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado con ID: " + id));
    }

    public List<Producto> buscarPorTermino(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listar();
        }
        return repo.buscarPorTermino(termino.trim());
    }

    // MÉTODO NUEVO PARA EL DASHBOARD
    public Long contarProductos() {
        return repo.count();
    }
}
