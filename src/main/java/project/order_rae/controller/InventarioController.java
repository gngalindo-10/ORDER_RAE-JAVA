package project.order_rae.controller;

import project.order_rae.model.Inventario;
import project.order_rae.service.InventarioService;
import project.order_rae.service.ProductoService;
import project.order_rae.service.UsuarioService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService servicio;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;

    public InventarioController(InventarioService servicio, ProductoService productoService, UsuarioService usuarioService) {
        this.servicio = servicio;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model modelo) {
        modelo.addAttribute("inventarios", servicio.listar());
        return "listar-inventario";
    }

    @GetMapping("/nuevo")
    public String nuevoFormulario(Model modelo) {
        modelo.addAttribute("inventario", new Inventario());
        modelo.addAttribute("productos", productoService.listar());
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "formulario-inventario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Inventario inventario) {
        servicio.insertar(inventario);
        return "redirect:/inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model modelo) {
        Inventario inventario = servicio.obtenerPorId(id);
        modelo.addAttribute("inventario", inventario);
        modelo.addAttribute("productos", productoService.listar());
        modelo.addAttribute("usuarios", usuarioService.listar());
        return "formulario-inventario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute Inventario inventario) {
        servicio.actualizar(id, inventario);
        return "redirect:/inventario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return "redirect:/inventario";
    }
}