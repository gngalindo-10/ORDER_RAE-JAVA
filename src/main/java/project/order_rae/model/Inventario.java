package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INVENTARIO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUCTO") 
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")  
    private Usuario usuario;

    @Column(name = "Cantidad")
    private Integer cantidad;

    @Column(name = "Created_at")
    private LocalDateTime createdAt;

    @Column(name = "Updated_at")
    private LocalDateTime updatedAt;

    // getters y setters

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
