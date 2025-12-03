package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.order_rae.model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Consulta nativa para filtrar productos
    @Query(value = "SELECT * FROM producto p \n" +
                "WHERE (:estado IS NULL OR p.Estado_producto = :estado) \n" +
                "AND (:precioMin IS NULL OR p.Precio_producto >= :precioMin) \n" +
                "AND (:precioMax IS NULL OR p.Precio_producto <= :precioMax) \n" +
                "AND (:busqueda IS NULL OR p.Referencia_producto LIKE CONCAT('%', :busqueda, '%') OR p.Codigo_producto LIKE CONCAT('%', :busqueda, '%'))",
        nativeQuery = true)
    List<Producto> findByFilters(
        @Param("estado") String estado,
        @Param("precioMin") Double precioMin,
        @Param("precioMax") Double precioMax,
        @Param("busqueda") String busqueda
    );
}