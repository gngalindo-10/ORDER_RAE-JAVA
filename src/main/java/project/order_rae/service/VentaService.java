package project.order_rae.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.order_rae.model.Venta;
import project.order_rae.repository.VentaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    // === CRUD ===

    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    public List<Venta> buscarPorTermino(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            return listarTodas();
        }
        try {
            Integer id = Integer.parseInt(termino.trim());
            Venta venta = ventaRepository.findById(id).orElse(null);
            return venta != null ? Collections.singletonList(venta) : new ArrayList<>();
        } catch (NumberFormatException e) {
            return new ArrayList<>();
        }
    }

    public Venta obtenerPorId(Integer id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    public void insertar(Venta venta) {
        ventaRepository.save(venta);
    }

    public void actualizar(Integer id, Venta ventaActualizada) {
        Venta existente = obtenerPorId(id);
        existente.setFechaVenta(ventaActualizada.getFechaVenta());
        existente.setTotalVenta(ventaActualizada.getTotalVenta());
        existente.setEstadoVenta(ventaActualizada.getEstadoVenta());
        existente.setPedidoId(ventaActualizada.getPedidoId());
        existente.setFidelizacionId(ventaActualizada.getFidelizacionId());
        ventaRepository.save(existente);
    }

    public void eliminar(Integer id) {
        ventaRepository.deleteById(id);
    }

    // === Dashboard ===

    public Double obtenerTotalVentas() {
        Double total = ventaRepository.sumarTotalVentas();
        return total != null ? total : 0.0;
    }

    /**
     * Retorna ventas de los últimos 6 meses, incluyendo el mes actual (diciembre).
     * Normaliza los nombres de los meses a minúsculas para coincidir con el formato generado en Java.
     */
    public Map<String, Double> obtenerVentasPorUltimos6Meses() {
        List<Object[]> resultados = ventaRepository.findVentasPorMesUltimos6Meses();

        Map<String, Double> ventasMap = new LinkedHashMap<>();
        for (Object[] fila : resultados) {
            String mes = (String) fila[0]; // Ej: "DIC", "NOV"
            Object totalObj = fila[1];

            Double total = 0.0;
            if (totalObj instanceof BigDecimal) {
                total = ((BigDecimal) totalObj).doubleValue();
            } else if (totalObj instanceof Double) {
                total = (Double) totalObj;
            } else if (totalObj != null) {
                try {
                    total = Double.parseDouble(totalObj.toString());
                } catch (NumberFormatException e) {
                    total = 0.0;
                }
            }

            // 🔑 Normalizar el mes de la BD a minúsculas: "DIC" → "dic"
            String mesNormalizado = (mes != null) ? mes.toLowerCase() : "desconocido";
            ventasMap.put(mesNormalizado, total);
        }

        // Generar los últimos 6 meses: [jul, ago, set, oct, nov, dic] (hoy = dic 2025)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM", new Locale("es", "PE"));
        LocalDate ahora = LocalDate.now();
        List<String> ultimos6Meses = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            LocalDate mes = ahora.minusMonths(i);
            String nombreMes = mes.format(formatter).substring(0, 3).toLowerCase();
            ultimos6Meses.add(nombreMes);
        }

        // Asegurar que todos los meses aparezcan (incluso diciembre)
        Map<String, Double> resultadoFinal = new LinkedHashMap<>();
        for (String mes : ultimos6Meses) {
            resultadoFinal.put(mes, ventasMap.getOrDefault(mes, 0.0));
        }

        return resultadoFinal;
    }
}