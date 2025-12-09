package project.order_rae.controller;

import project.order_rae.model.Venta;
import project.order_rae.service.VentaService;
import project.order_rae.utils.ExcelGenerator;
import project.order_rae.utils.PdfGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private ExcelGenerator excelGenerator;

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String termino, Model model) {
        var ventas = ventaService.buscarPorTermino(termino);
        model.addAttribute("ventas", ventas);
        model.addAttribute("termino", termino);
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
            ventaService.insertar(venta);
            redirectAttrs.addFlashAttribute("mensajeExito", "Venta creada exitosamente.");
            return "redirect:/ventas";
        } catch (DataIntegrityViolationException e) {
            redirectAttrs.addFlashAttribute("error", "Error: Verifique que los IDs de Pedido y Fidelización existan.");
            return "redirect:/ventas/nuevo";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
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

    @GetMapping("/reporte")
    public void generarReporte(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        var ventas = ventaService.buscarPorTermino(termino);
        pdfGenerator.generarPdf("venta_reporte", ventas, response);
    }

    @GetMapping("/excel")
    public void exportarExcel(@RequestParam(required = false) String termino, HttpServletResponse response) throws Exception {
        var ventas = ventaService.buscarPorTermino(termino);
        excelGenerator.generarExcelVentas(ventas, response);
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Integer id,
            @ModelAttribute Venta venta,
            RedirectAttributes redirectAttrs) {

        try {
            venta.setIdVenta(id);
            ventaService.actualizar(id, venta);
            redirectAttrs.addFlashAttribute("mensajeExito", "Venta actualizada exitosamente.");
            return "redirect:/ventas";
        } catch (DataIntegrityViolationException e) {
            redirectAttrs.addFlashAttribute("error", "Error: Verifique que los IDs de Pedido y Fidelización existan.");
            return "redirect:/ventas/editar/" + id;
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/ventas/editar/" + id;
        }
    }

    // Cambiado a @PostMapping
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        try {
            ventaService.eliminar(id);
            redirectAttrs.addFlashAttribute("mensajeExito", "Venta eliminada exitosamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "No se pudo eliminar la venta: " + e.getMessage());
        }
        return "redirect:/ventas";
    }
}