package project.order_rae.service;

import project.order_rae.repository.PedidoRepository;
import project.order_rae.repository.ProductoRepository;
import project.order_rae.repository.UsuarioRepository;
import project.order_rae.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public Map<String, Object> getDashboardData() {
        Map<String, Object> data = new HashMap<>();
        
        data.put("totalVentas", ventaRepository.countByEstadoVenta("COMPLETADA"));
        data.put("totalProductos", productoRepository.count());
        data.put("totalClientes", usuarioRepository.count());
        data.put("totalPedidos", pedidoRepository.count());
        data.put("ultimasVentas", ventaRepository.findTop5ByOrderByFechaVentaDesc());
        
        // Datos para gráfico
        Map<String, Object> datosGrafico = obtenerVentasMensuales();
        data.put("meses", datosGrafico.get("meses"));
        data.put("ventas", datosGrafico.get("ventas"));
        
        return data;
    }

    private Map<String, Object> obtenerVentasMensuales() {
        LocalDate hoy = LocalDate.now();
        LocalDate inicio = hoy.minusMonths(5).withDayOfMonth(1);
        LocalDate fin = hoy.withDayOfMonth(hoy.lengthOfMonth());
        
        List<Object[]> resultados = ventaRepository.getVentasMensuales(inicio, fin);
        
        String[] mesesEsp = {"", "Ene", "Feb", "Mar", "Abr", "May", "Jun", 
                             "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"};
        
        List<String> meses = new ArrayList<>();
        List<Double> ventas = new ArrayList<>();
        
        for (int i = 5; i >= 0; i--) {
            LocalDate mes = hoy.minusMonths(i);
            int numMes = mes.getMonthValue();
            meses.add(mesesEsp[numMes]);
            ventas.add(0.0);
        }
        
        for (Object[] fila : resultados) {
            int mesDb = ((Number) fila[0]).intValue();
            BigDecimal total = (BigDecimal) fila[1];
            
            for (int i = 0; i < meses.size(); i++) {
                if (mesesEsp[mesDb].equals(meses.get(i))) {
                    ventas.set(i, total != null ? total.doubleValue() : 0.0);
                    break;
                }
            }
        }
        
        Map<String, Object> resultado = new HashMap<>();
        resultado.put("meses", meses);
        resultado.put("ventas", ventas);
        return resultado;
    }
}