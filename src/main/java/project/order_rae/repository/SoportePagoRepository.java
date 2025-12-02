package project.order_rae.repository;

import project.order_rae.model.SoportePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoportePagoRepository extends JpaRepository<SoportePago, Long> {
    // Métodos personalizados si los necesitas
}