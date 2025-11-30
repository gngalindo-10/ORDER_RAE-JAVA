package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "VENTA")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Integer idVenta; 

    @Column(name = "Fecha_venta", nullable = false)
    private LocalDate fechaVenta; 

    @Column(name = "Total_venta", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalVenta; 

    @Column(name = "Estado_venta")
    private String estadoVenta; 

    // Relación con Pedido (FK)
    @Column(name = "pedido_id")
    private Integer pedidoId;

    // Relación con Fidelizacion (FK)
    @Column(name = "fidelizacion_id")
    private Integer fidelizacionId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Auto-generar fechas
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}