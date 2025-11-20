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
        return repo.save(inventario);
    }

    public Inventario actualizar(Long id, Inventario inventario) {
        Inventario existente = obtenerPorId(id);
        existente.setProducto(inventario.getProducto());
        existente.setUsuario(inventario.getUsuario());
        existente.setCantidad(inventario.getCantidad());
        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Inventario obtenerPorId(Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Inventario no encontrado con ID: " + id));
    }
}