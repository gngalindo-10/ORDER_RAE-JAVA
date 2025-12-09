package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.order_rae.model.Produccion;

import java.util.List;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long> {

    @Query("SELECT p FROM Produccion p JOIN FETCH p.usuario u WHERE " +
       "LOWER(p.codigoProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.categoriaProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.referenciaProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.colorProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.materialProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(p.cantidadProducto AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(p.estadoProducto) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Produccion> buscarPorTermino(@Param("termino") String termino);
}