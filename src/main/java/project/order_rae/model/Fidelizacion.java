package project.order_rae.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "fidelizacion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fidelizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FIDELIZACION")
    private Long id;

    @Column(name = "Fecha_de_fidelizacion", nullable = false)
    private LocalDate fechaFidelizacion;

    @Column(name = "Puntos_acumulados", nullable = false)
    private Integer puntosAcumulados;

    @Column(name = "Nivel_fidelizacion", nullable = false)
    private String nivelFidelizacion;

    @Column(name = "Created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "Updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarios_id", nullable = false)
    private Usuario usuario;

    // --- MÉTODO AUXILIAR PARA FORMATO DE FECHA ---
    public String getFechaFidelizacionFormateada() {
        if (fechaFidelizacion == null) return "—";
        return fechaFidelizacion.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}