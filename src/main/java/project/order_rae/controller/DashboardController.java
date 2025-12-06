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

import java.util.Arrays;

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

        // GERENTE o ADMIN 
        if (role.equals("ROLE_Gerente") || role.equals("ROLE_ADMIN")) {
            model.addAttribute("totalVentas", ventaService.countVentasActivas());
            model.addAttribute("totalProductos", productoService.contarProductos());
            model.addAttribute("totalClientes", usuarioService.contarUsuarios());
            model.addAttribute("totalPedidos", pedidoService.countPedidos());
            model.addAttribute("meses", Arrays.asList("Ene", "Feb", "Mar", "Abr", "May", "Jun"));
            model.addAttribute("ventas", Arrays.asList(1200000, 1800000, 2100000, 1900000, 2400000, 2700000));
            model.addAttribute("ultimasVentas", ventaService.findTop5ByOrderByFechaVentaDesc());
            return "dashboard";
        }

        // ASESOR COMERCIAL 
        if (role.equals("ROLE_Asesor Comercial")) {
            model.addAttribute("mensaje", "Dashboard para Asesor Comercial en desarrollo.");
            return "dashboard";
        }

        // JEFE LOGÍSTICO 
        if (role.equals("ROLE_Jefe Logistico")) {
            model.addAttribute("mensaje", "Dashboard para Jefe Logístico en desarrollo.");
            return "dashboard";
        }

        // CLIENTE 
        return "redirect:/";
    }
}