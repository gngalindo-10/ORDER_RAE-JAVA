package project.order_rae.repository;

import project.order_rae.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    long countByEstadoVenta(String estado);

    @Query("SELECT v FROM Venta v ORDER BY v.fechaVenta DESC")
    List<Venta> findTop5ByOrderByFechaVentaDesc();

    @Query(value = "SELECT MONTH(v.Fecha_venta) as mes, SUM(v.Total_venta) as total " +
                    "FROM VENTA v " +
                    "WHERE v.Fecha_venta >= :inicio AND v.Fecha_venta <= :fin " +
                    "GROUP BY MONTH(v.Fecha_venta) " +
                    "ORDER BY mes", nativeQuery = true)
    List<Object[]> getVentasMensuales(@Param("inicio") LocalDate inicio, 
                                    @Param("fin") LocalDate fin);

    // Búsqueda Multicriterio                                
    @Query("SELECT v FROM Venta v WHERE " +
       "LOWER(CAST(v.idVenta AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(v.fechaVenta AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(v.totalVenta AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(v.estadoVenta) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(v.pedidoId AS string)) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
       "LOWER(CAST(v.fidelizacionId AS string)) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Venta> buscarPorTermino(@Param("termino") String termino);                                
}