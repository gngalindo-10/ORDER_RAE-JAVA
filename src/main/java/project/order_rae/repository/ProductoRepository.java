package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.order_rae.model.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
Optional<Producto> findByReferenciaProducto(String referenciaProducto);
    // Consulta nativa para filtrar productos
    @Query(value = "SELECT * FROM producto p WHERE (?1 IS NULL OR p.Estado_producto = ?1) AND (?2 IS NULL OR p.Precio_producto >= ?2) AND (?3 IS NULL OR p.Precio_producto <= ?3) AND (?4 IS NULL OR p.Referencia_producto LIKE CONCAT('%', ?4, '%') OR p.Codigo_producto LIKE CONCAT('%', ?4, '%'))", nativeQuery = true)
List<Producto> findByFilters(String estado, Double precioMin, Double precioMax, String busqueda);
}