package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "PEDIDO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Integer idPedido;

    @Column(name = "Fecha_de_compra", nullable = false)
    private LocalDate fechaCompra;

    @Column(name = "Fecha_de_entrega", nullable = false)
    private LocalDate fechaEntrega;

    @Column(name = "Metodo_pago", nullable = false)
    private String metodoPago;

    @Column(name = "Total_de_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalDePago;

    @Column(name = "Estado_pedido", nullable = false)
    private String estadoPedido;

    @Column(name = "Created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "Updated_at")
    private LocalDateTime updatedAt;

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
