package project.order_rae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.order_rae.service.VentaService;
import project.order_rae.service.ProductoService;
import project.order_rae.service.UsuarioService;
import project.order_rae.service.PedidoService;

import java.util.*;

@Controller
public class DashboardController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String role = authentication.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElse("ROLE_Cliente");

        if ("ROLE_Gerente".equals(role) || "ROLE_ADMIN".equals(role)) {
            Double totalVentas = ventaService.obtenerTotalVentas();
            Long totalProductos = productoService.contarProductos();
            Long totalClientes = usuarioService.contarUsuarios();
            Long totalPedidos = pedidoService.countPedidos();

            // Nuevos conteos detallados
            Long clientes = usuarioService.contarPorRol("Cliente");
            Long asesores = usuarioService.contarPorRol("Asesor Comercial");
            Long jefesLogisticos = usuarioService.contarPorRol("Jefe Logistico");
            Long gerente = usuarioService.contarPorRol("Gerente");

            Long pendientes = pedidoService.contarPorEstado("Pendiente");
            Long enProceso = pedidoService.contarPorEstado("En proceso");
            Long enviados = pedidoService.contarPorEstado("Enviado");
            Long entregados = pedidoService.contarPorEstado("Entregado");
            Long cancelados = pedidoService.contarPorEstado("Cancelado");

            Map<String, Double> ventasPorMes = ventaService.obtenerVentasPorUltimos6Meses();
            List<String> meses = new ArrayList<>(ventasPorMes.keySet());
            List<Double> ventas = new ArrayList<>(ventasPorMes.values());

            model.addAttribute("totalVentas", totalVentas != null ? totalVentas : 0.0);
            model.addAttribute("totalProductos", totalProductos != null ? totalProductos : 0L);
            model.addAttribute("totalClientes", totalClientes != null ? totalClientes : 0L);
            model.addAttribute("totalPedidos", totalPedidos != null ? totalPedidos : 0L);

            // Atributos nuevos
            model.addAttribute("clientes", clientes != null ? clientes : 0L);
            model.addAttribute("asesores", asesores != null ? asesores : 0L);
            model.addAttribute("jefesLogisticos", jefesLogisticos != null ? jefesLogisticos : 0L);
            model.addAttribute("gerente", gerente != null ? gerente : 0L);

            model.addAttribute("pendientes", pendientes != null ? pendientes : 0L);
            model.addAttribute("enProceso", enProceso != null ? enProceso : 0L);
            model.addAttribute("enviados", enviados != null ? enviados : 0L);
            model.addAttribute("entregados", entregados != null ? entregados : 0L);
            model.addAttribute("cancelados", cancelados != null ? cancelados : 0L);

            model.addAttribute("meses", meses);
            model.addAttribute("ventas", ventas);

            return "dashboard";
        }

        if ("ROLE_Asesor Comercial".equals(role)) {
            model.addAttribute("mensaje", "Dashboard para Asesor Comercial en desarrollo.");
            return "dashboard";
        }

        if ("ROLE_Jefe Logistico".equals(role)) {
            model.addAttribute("mensaje", "Dashboard para Jefe Logístico en desarrollo.");
            return "dashboard";
        }

        return "redirect:/";
    }
}