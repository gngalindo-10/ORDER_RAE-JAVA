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

    // Método para actualizar SOLO desde el formulario de inventario
    public Producto actualizarDesdeInventario(Long id, Producto datosActualizados) {
        Producto existente = obtenerPorId(id);
        
        // Solo actualizamos los campos que el usuario puede editar en el formulario
        existente.setReferenciaProducto(datosActualizados.getReferenciaProducto());
        existente.setPrecio(datosActualizados.getPrecio());
        existente.setColor(datosActualizados.getColor());
        existente.setTipoDeMadera(datosActualizados.getTipoDeMadera());
        
        return repo.save(existente);
    }

    // Método para actualizar TODO el producto
    public Producto actualizar(Long id, Producto p) {
        Producto existente = obtenerPorId(id);
        
        existente.setCodigoProducto(p.getCodigoProducto());
        existente.setReferenciaProducto(p.getReferenciaProducto());
        existente.setColor(p.getColor());
        existente.setPrecio(p.getPrecio());
        existente.setEstadoProducto(p.getEstadoProducto());
        existente.setCantidadProducto(p.getCantidadProducto());
        existente.setTipoDeMadera(p.getTipoDeMadera());
        existente.setUsuario(p.getUsuario());
        existente.setCategoria(p.getCategoria());
        
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Producto obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> 
            new RuntimeException("Producto no encontrado con ID: " + id));
    }

    // Método para reportes
    public List<Producto> findFiltered(String estado, Double precioMin, Double precioMax, String busqueda) {
        return repo.findByFilters(estado, precioMin, precioMax, busqueda);
    }

    // Buscar producto por referencia (nombre)
    public Producto findByReferencia(String referencia) {
        return repo.findByReferenciaProducto(referencia).orElse(null);
    }
}