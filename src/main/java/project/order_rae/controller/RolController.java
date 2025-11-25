package project.order_rae.controller;

import project.order_rae.model.Rol;
import project.order_rae.service.RolService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RolController {

    private final RolService servicio;

    public RolController(RolService servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("roles", servicio.listar());
        return "listar-roles";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("rol", new Rol());
        return "formulario-rol";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Rol rol) {
        servicio.guardar(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Rol rol = servicio.obtenerPorId(String.valueOf(id));
        modelo.addAttribute("rol", rol);
        return "formulario-rol";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Rol rol) {
        servicio.actualizar(String.valueOf(id), rol);
        return "redirect:/roles";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(String.valueOf(id));
        return "redirect:/roles";
    }
}

