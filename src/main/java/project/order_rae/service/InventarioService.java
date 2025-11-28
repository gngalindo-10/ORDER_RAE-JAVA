package project.order_rae.service;

import project.order_rae.model.Inventario;
import project.order_rae.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository repo;

    public InventarioService(InventarioRepository repo) {
        this.repo = repo;
    }

    public List<Inventario> listar() {
        return repo.findAll();
    }

    public Inventario insertar(Inventario inventario) {
        if (inventario.getProducto() == null || inventario.getUsuario() == null) {
            throw new RuntimeException("Producto y Usuario son obligatorios");
        }
        if (inventario.getCantidad() == null || inventario.getCantidad() < 0) {
            throw new RuntimeException("La cantidad debe ser mayor o igual a 0");
        }
        if (inventario.getBodega() == null || inventario.getBodega().trim().isEmpty()) {
            inventario.setBodega("BODEGA_PRINCIPAL"); 
        }
        if (inventario.getEstado() == null || inventario.getEstado().trim().isEmpty()) {
            inventario.setEstado("DISPONIBLE"); 
        }
        return repo.save(inventario);
    }

    public Inventario actualizar(Long id, Inventario inventario) {
        Inventario existente = obtenerPorId(id);
        existente.setProducto(inventario.getProducto());
        existente.setUsuario(inventario.getUsuario());
        existente.setCantidad(inventario.getCantidad());
        existente.setBodega(inventario.getBodega());
        existente.setEstado(inventario.getEstado());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Inventario obtenerPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }

    // Método para buscar por producto 
    public List<Inventario> findByProductoId(Long productoId) {
        return repo.findByProductoId(productoId);
    }

    // Método para buscar por bodega 
    public List<Inventario> findByBodega(String bodega) {
        return repo.findByBodega(bodega);
    }
}