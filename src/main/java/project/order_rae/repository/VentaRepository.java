package project.order_rae.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import project.order_rae.model.Venta;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query("SELECT COALESCE(SUM(v.totalVenta), 0.0) FROM Venta v WHERE v.estadoVenta = 'completada'")
    Double sumarTotalVentas();

    // Incluye el mes actual (hasta hoy) y los últimos 5 meses completos
    @Query(value = """
        SELECT 
            SUBSTRING(UPPER(MONTHNAME(v.Fecha_venta)), 1, 3) AS mes,
            COALESCE(SUM(v.Total_venta), 0.0) AS total
        FROM VENTA v
        WHERE v.Fecha_venta >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH)
          AND v.Estado_venta = 'completada'
        GROUP BY YEAR(v.Fecha_venta), MONTH(v.Fecha_venta)
        ORDER BY v.Fecha_venta
        """, nativeQuery = true)
    List<Object[]> findVentasPorMesUltimos6Meses();
}