package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.order_rae.model.Produccion;

@Repository
public interface ProduccionRepository extends JpaRepository<Produccion, Long> {
    
}