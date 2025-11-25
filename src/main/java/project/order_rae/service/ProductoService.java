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
        existente.setUsuario(p.getUsuario());     // Asigna usuario
        existente.setCategoria(p.getCategoria()); // Asigna categoría
        
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Producto obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> 
            new RuntimeException("Producto no encontrado con ID: " + id));
    }

    // Método corregido para reportes
    public List<Producto> findFiltered(String estado, Double precioMin, Double precioMax, String busqueda) {
        return repo.findByFilters(estado, precioMin, precioMax, busqueda);
    }
}