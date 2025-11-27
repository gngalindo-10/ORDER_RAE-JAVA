package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "Codigo_producto", nullable = false, unique = true)
    private String codigoProducto;

    @Column(name = "Referencia_producto", nullable = false)
    private String referenciaProducto;

    @Column(name = "Color_producto", nullable = false)
    private String color;

    @Column(name = "Precio_producto", nullable = false)
    private Double precio;

    @Column(name = "Estado_producto", nullable = false)
    private String estadoProducto;
    
    @Column(name = "Cantidad_producto", nullable = false)
    private String cantidadProducto;

    @Column(name = "Tipo_De_Madera", nullable = false, length = 45)
    private String tipoDeMadera;

    @Column(name = "Created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "Updated_at")
    private LocalDateTime updatedAt;

    // Relación con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuario;

    // Relación con Inventario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    // Relación con Categoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Fechas automáticamente
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