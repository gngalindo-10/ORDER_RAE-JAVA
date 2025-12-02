package project.order_rae.controller;

import project.order_rae.model.SoportePago;
import project.order_rae.service.SoportePagoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/soportePago")
public class SoportePagoController {

    private final SoportePagoService soportePagoService;

    public SoportePagoController(SoportePagoService soportePagoService) {
        this.soportePagoService = soportePagoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("soportes", soportePagoService.findAll());
        return "soportePago/listarSoportePago";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model model) {
        model.addAttribute("soportePago", new SoportePago());
        return "soportePago/formSoportePago";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute SoportePago soportePago, RedirectAttributes redirectAttrs) {
        soportePagoService.save(soportePago);
        redirectAttrs.addFlashAttribute("mensaje", "Soporte de pago creado exitosamente.");
        return "redirect:/soportePago";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        SoportePago soporte = soportePagoService.findById(id).orElse(new SoportePago());
        model.addAttribute("soportePago", soporte);
        return "soportePago/formSoportePago";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @ModelAttribute SoportePago soportePago,
            RedirectAttributes redirectAttrs) {

        soportePago.setIdSoportePago(id);
        soportePagoService.save(soportePago);
        redirectAttrs.addFlashAttribute("mensaje", "Soporte de pago actualizado exitosamente.");
        return "redirect:/soportePago";
    }

    // ✅ CORREGIDO: Cambiado de @GetMapping a @PostMapping
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        soportePagoService.deleteById(id);
        redirectAttrs.addFlashAttribute("mensaje", "Soporte de pago eliminado exitosamente.");
        return "redirect:/soportePago";
    }
} 