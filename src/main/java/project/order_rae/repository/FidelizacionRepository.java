package project.order_rae.repository;

import project.order_rae.model.Fidelizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FidelizacionRepository extends JpaRepository<Fidelizacion, Long> {

    // Buscar por nivel de fidelización
    List<Fidelizacion> findByNivelFidelizacion(String nivel);

    // Buscar por rango de puntos acumulados
    List<Fidelizacion> findByPuntosAcumuladosBetween(Integer min, Integer max);

    // Opcional: buscar por ID de usuario (si lo necesitas en el futuro)
    List<Fidelizacion> findByUsuario_Id(Long usuarioId);

    // Búsqueda Multicriterio
    @Query("SELECT f FROM Fidelizacion f JOIN FETCH f.usuario u WHERE " +
       "LOWER(CAST(f.id AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(f.fechaFidelizacion AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(f.puntosAcumulados AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(f.nivelFidelizacion) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(u.apellidos) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Fidelizacion> buscarPorTermino(@Param("termino") String termino);
}