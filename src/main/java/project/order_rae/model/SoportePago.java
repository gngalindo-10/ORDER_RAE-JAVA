package project.order_rae.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "soporte_pago")
public class SoportePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SOPORTE_PAGO")
    private Long idSoportePago;

    @Column(name = "fecha_hora_pago", nullable = false)
    private LocalDateTime fechaHoraPago;

    @Column(name = "total_pago", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPago;

    @Column(name = "soporte_url")
    private String soporteUrl;

    @Column(name = "estado_soporte")
    private String estadoSoporte;

    // ✅ CORREGIDO: nombres en minúscula para coincidir con la base de datos
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "usuarios_id")
    private Long usuariosId;

    @Column(name = "venta_id")
    private Long ventaId;

    // Constructor vacío
    public SoportePago() {}

    // Getters y Setters
    public Long getIdSoportePago() {
        return idSoportePago;
    }

    public void setIdSoportePago(Long idSoportePago) {
        this.idSoportePago = idSoportePago;
    }

    public LocalDateTime getFechaHoraPago() {
        return fechaHoraPago;
    }

    public void setFechaHoraPago(LocalDateTime fechaHoraPago) {
        this.fechaHoraPago = fechaHoraPago;
    }

    public BigDecimal getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(BigDecimal totalPago) {
        this.totalPago = totalPago;
    }

    public String getSoporteUrl() {
        return soporteUrl;
    }

    public void setSoporteUrl(String soporteUrl) {
        this.soporteUrl = soporteUrl;
    }

    public String getEstadoSoporte() {
        return estadoSoporte;
    }

    public void setEstadoSoporte(String estadoSoporte) {
        this.estadoSoporte = estadoSoporte;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(Long usuariosId) {
        this.usuariosId = usuariosId;
    }

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }



    // ✅ Métodos para auto-generar fechas
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