package project.order_rae.repository;

import project.order_rae.model.Fidelizacion;
import org.springframework.data.jpa.repository.JpaRepository;
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
}