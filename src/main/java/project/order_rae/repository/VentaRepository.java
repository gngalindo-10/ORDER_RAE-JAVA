package project.order_rae.repository;

import project.order_rae.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Modifying
    @Query("DELETE FROM Venta v WHERE v.idVenta = :id")
    void deleteByIdCustom(@Param("id") Integer id);
}