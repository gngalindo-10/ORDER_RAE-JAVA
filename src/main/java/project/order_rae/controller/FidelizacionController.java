package project.order_rae.controller;

import project.order_rae.model.Fidelizacion;
import project.order_rae.service.FidelizacionService;
import project.order_rae.service.UsuarioService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/fidelizacion")
public class FidelizacionController {

    private final FidelizacionService servicio;
    private final UsuarioService usuarioService;

    public FidelizacionController(FidelizacionService servicio, UsuarioService usuarioService) {
        this.servicio = servicio;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("fidelizaciones", servicio.listar());
        return "fidelizacion/listarFidelizacion";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("fidelizacion", new Fidelizacion());
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "fidelizacion/formFidelizacion";
    }

    @PostMapping("/guardar")
    public String guardar(
            @RequestParam Long usuarioId,
            @ModelAttribute Fidelizacion fidelizacion,
            RedirectAttributes redirectAttrs) {

        fidelizacion.setUsuario(usuarioService.obtenerPorId(usuarioId));
        servicio.insertar(fidelizacion);
        redirectAttrs.addFlashAttribute("mensajeExito", "Fidelización creada exitosamente.");
        return "redirect:/fidelizacion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Fidelizacion fidelizacion = servicio.obtenerPorId(id);
        modelo.addAttribute("fidelizacion", fidelizacion);
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "fidelizacion/formFidelizacion";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @RequestParam Long usuarioId,
            @ModelAttribute Fidelizacion fidelizacion,
            RedirectAttributes redirectAttrs) {

        fidelizacion.setId(id);
        fidelizacion.setUsuario(usuarioService.obtenerPorId(usuarioId));
        servicio.actualizar(id, fidelizacion);
        redirectAttrs.addFlashAttribute("mensajeExito", "Fidelización actualizada exitosamente.");
        return "redirect:/fidelizacion";
    }

    // Cambiado a @PostMapping
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        servicio.eliminar(id);
        redirectAttrs.addFlashAttribute("mensajeExito", "Fidelización eliminada exitosamente.");
        return "redirect:/fidelizacion";
    }
}