package project.order_rae.service;

import java.util.stream.Collectors;
import project.order_rae.dto.ProductoReporteDTO;
import project.order_rae.model.Categoria;
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

    public List<Categoria> listarCategorias() {
        return repo.findAllCategorias(); // Asumiendo que tu repositorio tiene este método
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

    // Método para actualizar todos los campos del producto
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
    public List<ProductoReporteDTO> findReporteFiltered(
        String codigo,
        String referencia,
        Long categoria,
        String color,
        String estado,
        String tipoMadera,
        String bodega) {

        List<Object[]> resultados = repo.findReporteData(
            codigo, referencia, categoria, color, estado, tipoMadera, bodega);

        return resultados.stream()
            .map(ProductoReporteDTO::new)
            .collect(Collectors.toList());
    }

    // Buscar producto por referencia (nombre)
    public Producto findByReferencia(String referencia) {
        return repo.findByReferenciaProducto(referencia).orElse(null);
    }
}