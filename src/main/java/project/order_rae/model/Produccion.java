package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "produccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCCION")
    private Long idProduccion;

    @Column(name = "Codigo_producto", nullable = false, length = 100)
    private String codigoProducto;

    @Column(name = "Categoria_producto", nullable = false, length = 45)
    private String categoriaProducto;

    @Column(name = "Referencia_producto", nullable = false, length = 100)
    private String referenciaProducto;

    @Column(name = "Color_producto", nullable = false, length = 100)
    private String colorProducto;

    @Column(name = "Material_producto", nullable = false, length = 100)
    private String materialProducto;

    @Column(name = "Cantidad_producto", nullable = false)
    private Integer cantidadProducto;

    @Column(name = "Estado_producto", nullable = false, length = 45)
    private String estadoProducto;

    @Column(name = "Created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "Updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuario;

    // Relación con Producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Métodos para auditoría
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