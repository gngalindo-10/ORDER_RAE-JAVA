package project.order_rae.controller;

import project.order_rae.model.Venta;
import project.order_rae.model.Pedido;
import project.order_rae.model.Fidelizacion;
import project.order_rae.service.VentaService;
import project.order_rae.service.PedidoService;
import project.order_rae.service.FidelizacionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final PedidoService pedidoService;
    private final FidelizacionService fidelizacionService;

    public VentaController(
            VentaService ventaService,
            PedidoService pedidoService,
            FidelizacionService fidelizacionService) {
        this.ventaService = ventaService;
        this.pedidoService = pedidoService;
        this.fidelizacionService = fidelizacionService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listar());
        return "venta/listarVenta";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("venta", new Venta());
        return "venta/formVenta";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, RedirectAttributes redirectAttrs) {
        try {
            // ✅ Obtener un ID válido de PEDIDO existente
            List<Pedido> pedidos = pedidoService.listar();
            Integer pedidoId;
            if (pedidos.isEmpty()) {
                pedidoId = 1; // Fallback si no hay pedidos
            } else {
                int index = (ventaService.listar().size() + 1) % pedidos.size();
                 pedidoId = pedidos.get(index).getIdPedido(); // ✅ Usa 'index' // ✅ Usar getIdPedido()
            }

            // ✅ Obtener un ID válido de FIDELIZACION existente
            List<Fidelizacion> fidelizaciones = fidelizacionService.listar();
            Long fidelizacionIdLong; // ← Es Long en Fidelizacion
            if (fidelizaciones.isEmpty()) {
                fidelizacionIdLong = 1L; // Fallback si no hay fidelizaciones
            } else {
                 // ✅ Usar el tamaño actual + 1 para calcular el índice
            int index = (ventaService.listar().size() + 1) % fidelizaciones.size();
            fidelizacionIdLong = fidelizaciones.get(index).getId(); // ✅ Usa 'index' y getId // ✅ Usar getId() (porque el campo se llama "id")
            }

            // ✅ Convertir a Integer para que coincida con Venta.java
            Integer fidelizacionId = fidelizacionIdLong.intValue();

            // ✅ Asignar a la venta (ahora los tipos coinciden: Integer → Integer)
            venta.setPedidoId(pedidoId);
            venta.setFidelizacionId(fidelizacionId);

            // ✅ Guardar la venta
            ventaService.insertar(venta);
            redirectAttrs.addFlashAttribute("mensaje", "Venta creada exitosamente.");
            return "redirect:/ventas";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Error al crear la venta. Por favor, inténtelo de nuevo.");
            return "redirect:/ventas/nuevo";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Venta venta = ventaService.obtenerPorId(id);
        
        if (venta.getEstadoVenta() != null) {
            String estado = venta.getEstadoVenta().trim();
            if ("completada".equalsIgnoreCase(estado)) {
                venta.setEstadoVenta("Completada");
            } else if ("pendiente".equalsIgnoreCase(estado)) {
                venta.setEstadoVenta("Pendiente");
            } else if ("cancelada".equalsIgnoreCase(estado)) {
                venta.setEstadoVenta("Cancelada");
            }
        }
        
        model.addAttribute("venta", venta);
        return "venta/formVenta";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Integer id,
            @ModelAttribute Venta venta,
            RedirectAttributes redirectAttrs) {

        try {
            venta.setIdVenta(id);
            ventaService.actualizar(id, venta);
            redirectAttrs.addFlashAttribute("mensaje", "Venta actualizada exitosamente.");
            return "redirect:/ventas";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al actualizar la venta. Por favor, inténtelo de nuevo.");
            return "redirect:/ventas/editar/" + id;
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        try {
            ventaService.eliminar(id);
            redirectAttrs.addFlashAttribute("mensaje", "Venta eliminada exitosamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "No se pudo eliminar la venta.");
        }
        return "redirect:/ventas";
    }
}