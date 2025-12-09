package project.order_rae.repository;

import project.order_rae.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE " +
           "LOWER(CAST(p.idPedido AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(CAST(p.fechaCompra AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(CAST(p.fechaEntrega AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(p.metodoPago) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(CAST(p.totalDePago AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(p.estadoPedido) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Pedido> buscarPorTermino(@Param("termino") String termino);
}
