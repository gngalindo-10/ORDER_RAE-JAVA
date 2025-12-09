package project.order_rae.repository;

import project.order_rae.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c WHERE " +
       "LOWER(CAST(c.id AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(c.nombreCategoria) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(c.estadoCategoria) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Categoria> buscarPorTermino(@Param("termino") String termino);
    // Filtrar por estado
    List<Categoria> findByEstadoCategoriaIgnoreCase(String estado); 
}