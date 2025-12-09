package project.order_rae.service;

import project.order_rae.model.Venta;
import project.order_rae.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    // Métodos existentes
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta obtenerPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    public Venta insertar(Venta venta) {
        if (venta.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria.");
        }
        if (venta.getTotalVenta() == null || venta.getTotalVenta().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El total de venta debe ser un valor positivo.");
        }
        return ventaRepository.save(venta);
    }

    public Venta actualizar(Integer id, Venta ventaActualizada) {
        Venta ventaExistente = obtenerPorId(id);
        if (ventaActualizada.getFechaVenta() == null) {
            throw new IllegalArgumentException("La fecha de venta es obligatoria.");
        }
        if (ventaActualizada.getTotalVenta() == null || ventaActualizada.getTotalVenta().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El total de venta debe ser un valor positivo.");
        }

        ventaExistente.setFechaVenta(ventaActualizada.getFechaVenta());
        ventaExistente.setTotalVenta(ventaActualizada.getTotalVenta());
        ventaExistente.setEstadoVenta(ventaActualizada.getEstadoVenta());
        ventaExistente.setPedidoId(ventaActualizada.getPedidoId());
        ventaExistente.setFidelizacionId(ventaActualizada.getFidelizacionId());

        return ventaRepository.save(ventaExistente);
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada con ID: " + id);
        }
        ventaRepository.deleteById(id);
    }

    // NUEVOS MÉTODOS PARA DASHBOARD
    public long countVentasActivas() {
        return ventaRepository.countByEstadoVenta("Activa");
    }

    public List<Venta> findTop5ByOrderByFechaVentaDesc() {
        return ventaRepository.findTop5ByOrderByFechaVentaDesc();
    }

    public List<Venta> buscarPorTermino(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listar();
        }
        return ventaRepository.buscarPorTermino(termino.trim());
    }
}