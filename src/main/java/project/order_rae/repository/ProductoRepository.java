package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import project.order_rae.model.Producto;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
@Query("SELECT p FROM Producto p JOIN FETCH p.usuario u JOIN FETCH p.categoria c WHERE " +
       "LOWER(p.codigoProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.referenciaProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.color) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.estadoProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(p.precio AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(c.nombreCategoria) LIKE LOWER(CONCAT('%', :termino, '%'))")
List<Producto> buscarPorTermino(@Param("termino") String termino);
}