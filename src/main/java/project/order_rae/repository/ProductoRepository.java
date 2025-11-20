package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.order_rae.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}