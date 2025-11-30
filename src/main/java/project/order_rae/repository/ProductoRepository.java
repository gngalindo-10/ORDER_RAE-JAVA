package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.order_rae.model.Producto;
import project.order_rae.model.Categoria;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByReferenciaProducto(String referenciaProducto);

    // Método para filtrar productos según múltiples criterios
        @Query(value = """
            SELECT 
                p.ID_PRODUCTO,
                p.Codigo_producto,
                p.Referencia_producto,
                p.Color_producto,
                p.Tipo_De_Madera,
                p.Estado_producto,
                i.Bodega,
                CONCAT(u.nombres, ' ', u.apellidos),
                c.nombre_categoria
            FROM producto p
            LEFT JOIN inventario i ON p.ID_PRODUCTO = i.producto_id
            LEFT JOIN usuarios u ON p.usuarios_id = u.id_usuario
            LEFT JOIN categorias c ON p.categoria_id = c.id_categoria
            WHERE (?1 IS NULL OR p.Codigo_producto LIKE CONCAT('%', ?1, '%'))
            AND (?2 IS NULL OR p.Referencia_producto LIKE CONCAT('%', ?2, '%'))
            AND (?3 IS NULL OR p.categoria_id = ?3)
            AND (?4 IS NULL OR p.Color_producto = ?4)
            AND (?5 IS NULL OR p.Estado_producto = ?5)
            AND (?6 IS NULL OR p.Tipo_De_Madera = ?6)
            AND (?7 IS NULL OR i.Bodega = ?7)
            """, nativeQuery = true)
        List<Object[]> findReporteData(
            String codigo,
            String referencia,
            Long categoria,
            String color,
            String estado,
            String tipoMadera,
            String bodega
        );
    
    // Método para obtener todas las categorías (usado en el formulario de reportes)
    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.categoria IS NOT NULL")
    List<Categoria> findAllCategorias();
}