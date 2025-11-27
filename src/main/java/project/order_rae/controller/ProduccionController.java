package project.order_rae.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.order_rae.model.Produccion;
import project.order_rae.service.ProduccionService;
import project.order_rae.service.UsuarioService;
import project.order_rae.service.ProductoService;

import java.util.List;

@Controller
@RequestMapping("/produccion")
public class ProduccionController {

    @Autowired
    private ProduccionService produccionService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listar(Model model) {
        List<Produccion> producciones = produccionService.listar();
        model.addAttribute("producciones", producciones);
        return "produccion/listarProduccion";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("produccion", new Produccion());
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("productos", productoService.listar());
        return "produccion/formProduccion";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Produccion produccion,
                          @RequestParam Long usuariosId,
                          @RequestParam(required = false) Long productoId) { 
        produccion.setUsuario(usuarioService.obtenerPorId(usuariosId));
        
        if (productoId != null) {
            produccion.setProducto(productoService.obtenerPorId(productoId));
        }

        produccionService.guardar(produccion);
        return "redirect:/produccion";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Produccion produccion = produccionService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Producción no encontrada"));
        model.addAttribute("produccion", produccion);
        model.addAttribute("usuarios", usuarioService.listar());
        model.addAttribute("productos", productoService.listar()); 
        return "produccion/formProduccion"; 
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id,
                             @ModelAttribute Produccion produccion,
                             @RequestParam Long usuariosId,
                             @RequestParam(required = false) Long productoId) { 
        produccion.setIdProduccion(id);
        produccion.setUsuario(usuarioService.obtenerPorId(usuariosId));

        if (productoId != null) {
            produccion.setProducto(productoService.obtenerPorId(productoId));
        }

        produccionService.guardar(produccion);
        return "redirect:/produccion";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        produccionService.eliminar(id);
        return "redirect:/produccion";
    }
}